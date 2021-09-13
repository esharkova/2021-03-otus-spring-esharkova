package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.enums.FixFlag;
import ru.diasoft.micro.enums.PositionDateKind;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.SendChangePositionDto;
import ru.diasoft.micro.repository.PositionRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Primary
@Loggable
@Service
public class CalculatePositionServiceImpl implements CalculatePositionService{

    private final PositionService positionService;
    private final PositionRepository positionRepository;
    private final MessageSendingService messageSendingService;
    private final TransformOperation transformOperation;

    private static final DSLogger logger = DSLogManager.getLogger(CalculatePositionServiceImpl.class);

    @Override
    public Position findPositonByKeyParams(Position position) {

        return positionRepository.findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndFixFlag(
                position.getPortfolioStructureID(),
                position.getAssetID(),
                position.getPositionType(),
                position.getPositionDateKind(),
                FixFlag.NOT_FIXED.getValue()
        );
    }

    @Transactional
    @Override
    public void savePosition(Position changePosition) {
        Position createdPosition;
        List<Position> sendPositionList = new LinkedList<>();
        List<Position> calcPositionList;


        Position previousPosition = findPositonByKeyParams(changePosition);

        if ( previousPosition!=null )
        {
            //если срез уже есть, пересчитываем его и сохраняем
            createdPosition = positionService.createPosition(recalculatePosition(previousPosition,changePosition));

            positionService.deletePosition(previousPosition);
        }
        else {
            createdPosition = positionService.createPosition(changePosition);
        }

        sendPositionList.add(createdPosition);

        calcPositionList = recalculateNextPosition(createdPosition);
        sendPositionList.addAll(calcPositionList);

        if (changePosition.getPositionDateKind().equals(PositionDateKind.TX.getValue()))
        {
            saveDetailPosition(changePosition);
        }


        SendChangePositionDto sendChangePositionDto = transformOperation.transformPositionToDto(sendPositionList);
        logger.info(new StringBuilder(" Результат transformPositionToDto: ")
                .append(sendChangePositionDto.toString())
                .toString());

        messageSendingService.sendChangePositionMessage(sendChangePositionDto);
    }

    public Position recalculatePosition(Position previousPosition, Position changePosition){

        Position newPosition = new Position(previousPosition);
        newPosition.setIncome(previousPosition.getIncome().add(changePosition.getIncome()));
        newPosition.setExpense(previousPosition.getExpense().add(changePosition.getExpense()));
        newPosition.setOutRest((previousPosition.getIncomeRest().add(previousPosition.getIncome()).add(changePosition.getIncome())).subtract(previousPosition.getExpense()).subtract(changePosition.getExpense()));

        if (changePosition.getPositionDateKind()== 0){
            newPosition.setOutRest(BigDecimal.ZERO);
            newPosition.setIncomeRest(BigDecimal.ZERO);
        }

        return newPosition;
    }

    @Override
    public List<Position> recalculateNextPosition(Position createdPosition) {

        List<Position> newNextPositionList = new LinkedList<>();
        Position nextPosition, newNextPosition;
        int countSlice = 0;

        //проходим все срезы с PositionDateKind > созданного или пересчитанного
        for(int i = createdPosition.getPositionDateKind(); i< PositionDateKind.TX.getValue(); i++){

            nextPosition = positionRepository.findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndFixFlag(
                    createdPosition.getPortfolioStructureID(),
                    createdPosition.getAssetID(),
                    createdPosition.getPositionType(),
                    positionService.getNextPositionDateKind(i),
                    FixFlag.NOT_FIXED.getValue());

            //если нашли , то пересчитаем вх и исх остаток c учетом оборотов пердыдущих срезов
            if (nextPosition!=null){

                countSlice = newNextPositionList.size();

                newNextPosition = new Position(nextPosition);

                if (countSlice==0){
                    newNextPosition.setIncomeRest(createdPosition.getOutRest());
                    newNextPosition.setOutRest(createdPosition.getOutRest().add(nextPosition.getIncome()).subtract(nextPosition.getExpense()));
                }
                else {
                    newNextPosition.setIncomeRest(newNextPositionList.get(countSlice-1).getOutRest());
                    newNextPosition.setOutRest(newNextPositionList.get(countSlice-1).getOutRest().add(nextPosition.getIncome()).subtract(nextPosition.getExpense()));
                }

                newNextPosition.setIncome(nextPosition.getIncome());
                newNextPosition.setExpense(nextPosition.getExpense());

                newNextPositionList.add(newNextPosition);
                positionService.deletePosition(nextPosition);
            }
            //если не нашли, то создадим срезы
            else{
                if (positionService.checkPositionDateKind(positionService.getNextPositionDateKind(i))){
                    newNextPosition = new Position(createdPosition);
                    newNextPosition.setPositionDateKind(positionService.getNextPositionDateKind(i));
                    newNextPosition.setIncomeRest(createdPosition.getOutRest());
                    newNextPosition.setIncome(BigDecimal.ZERO);
                    newNextPosition.setExpense(BigDecimal.ZERO);
                    newNextPosition.setOutRest(createdPosition.getOutRest());
                    newNextPositionList.add(newNextPosition);}
                else {
                    logger.info(new StringBuilder("Некорректный тип позиции: ")
                            .append(positionService.getNextPositionDateKind(i).toString())
                            .toString());
                }

            }
        }
        return positionService.savePositionList(newNextPositionList);
    }

    @Override
    public void saveDetailPosition(Position changePosition) {

        Position detailPosition = new Position(changePosition);
        detailPosition.setPositionDateKind(0);
        detailPosition.setOperationDate(changePosition.getOperationDate());
        detailPosition.setIncomeRest(BigDecimal.ZERO);
        detailPosition.setIncome(changePosition.getIncome());
        detailPosition.setExpense(changePosition.getExpense());
        detailPosition.setOutRest(BigDecimal.ZERO);


        Position previousDetailPosition = positionRepository.findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndOperationDate(
                changePosition.getPortfolioStructureID(),
                changePosition.getAssetID(),
                changePosition.getPositionType(),
                PositionDateKind.TDETAIL.getValue(),
                changePosition.getOperationDate()
        );

        if ( previousDetailPosition!=null )
        {
            //если срез уже есть, пересчитываем его и сохраняем
            positionService.createPosition(recalculatePosition(previousDetailPosition,detailPosition));

            positionService.deletePosition(previousDetailPosition);
        }
        else {
            positionService.createPosition(detailPosition);
        }

    }

    @Override
    public Position reCreatePosition(Position changePosition, Position previousPosition) {

        Position createdPosition = positionService.createPosition(recalculatePosition(previousPosition,changePosition));

        positionService.deletePosition(previousPosition);

        return createdPosition;
    }

}