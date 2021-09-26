package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.enrichment.SecurityEnrichment;
import ru.diasoft.micro.enums.FixFlag;
import ru.diasoft.micro.enums.PositionDateKind;
import ru.diasoft.micro.enums.PositionDayDiff;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.repository.PositionRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Primary
@Loggable
@Service
public class FixPositionServiceImpl implements FixPositionService {
    private final PositionService positionService;
    private final PositionRepository positionRepository;
    private final SecurityEnrichment securityEnrichment;

    List<Position> deletePositionList = new LinkedList<>();

    @Override
    public List<Position> findNotFixPosition() {

        return positionService.findByFixFlagAndPositionDateKindGreaterThan(FixFlag.NOT_FIXED.getValue(), PositionDateKind.TMINUS_1.getValue());

    }

    @Transactional
    @Override
    public void fixPosition() {

        ZonedDateTime currentDate = ZonedDateTime.now();
        List<Position> newPositionList = new LinkedList<>();
        List<Long> positionIDList = new LinkedList<>();

        List<Position> fixPositionList = findNotFixPosition();

        for(Position position: fixPositionList){

            Position newPosition = createNewDayPosition(position);

            newPositionList.add(newPosition);
            positionIDList.add(position.getPositionID());

            if (newPosition.getPositionDateKind().equals(PositionDateKind.T2.getValue())){
                newPositionList.add(calculateTxPosition(newPosition));
            }

        }

        positionService.savePositionList(newPositionList);
        positionService.setFixFlag(positionIDList, FixFlag.FIXED.getValue(), currentDate);
        positionRepository.deleteAll(deletePositionList);
        //при фиксации позиции необходимо загрузить ценные бумаги
        securityEnrichment.enrich(positionRepository.findDistinctAssetID());
    }



    private Position createNewDayPosition(Position position) {

        BigDecimal turnover = BigDecimal.ZERO;

        Position newDayPosition = new Position(position);

        newDayPosition.setPositionDateTime(ZonedDateTime.now());
        newDayPosition.setPositionDateKind(positionService.getPreviousPositionDateKind(position.getPositionDateKind()));
        newDayPosition.setIncome(BigDecimal.ZERO);
        newDayPosition.setExpense(BigDecimal.ZERO);

        if (positionService.getPreviousPositionDateKind(position.getPositionDateKind()).equals(PositionDateKind.T2.getValue())){

            Position detailPosition = positionRepository.findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndOperationDate(
                            position.getPortfolioStructureID(),
                            position.getAssetID(),
                            position.getPositionType(),
                            PositionDateKind.TDETAIL.getValue(),
                            ZonedDateTime.now().toLocalDateTime().toLocalDate().plusDays(PositionDayDiff.DAYS_TX.getValue())
                    );

            if (detailPosition != null) {
                turnover = turnover.add(detailPosition.getIncome().subtract(detailPosition.getExpense()));
                deletePositionList.add(detailPosition);
            }

            newDayPosition.setIncomeRest(position.getIncomeRest());
            newDayPosition.setOutRest(position.getIncomeRest().add(turnover));
        }
        else {
            newDayPosition.setIncomeRest(position.getOutRest());
            newDayPosition.setOutRest(position.getOutRest());
        }

        return newDayPosition;
    }


    private Position calculateTxPosition(Position position) {
        BigDecimal txTurnover = BigDecimal.ZERO;

        Position txPosition = new Position(position);
        txPosition.setPositionDateKind(PositionDateKind.TX.getValue());
        txPosition.setIncomeRest(position.getOutRest());

        List<Position> detailPositionList = positionRepository.findByPortfolioStructureIDAndAssetIDAndPositionTypeAndPositionDateKindAndOperationDateGreaterThan(
                position.getPortfolioStructureID(),
                position.getAssetID(),
                position.getPositionType(),
                PositionDateKind.TDETAIL.getValue(),
                ZonedDateTime.now().toLocalDateTime().toLocalDate().plusDays(PositionDayDiff.DAYS_TX.getValue())
                );

        if (detailPositionList != null) {

            for(Position detailPosition: detailPositionList){
                txTurnover = txTurnover.add(detailPosition.getIncome().subtract(detailPosition.getExpense()));
            }

        }

        txPosition.setIncome(BigDecimal.ZERO);
        txPosition.setExpense(BigDecimal.ZERO);
        txPosition.setOutRest(position.getOutRest().add(txTurnover));

        return txPosition;
    }

}
