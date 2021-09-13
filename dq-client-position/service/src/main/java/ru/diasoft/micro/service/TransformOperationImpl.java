package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.domain.*;
import ru.diasoft.micro.domain.Currency;
import ru.diasoft.micro.enums.*;
import ru.diasoft.micro.event.subscriber.ReceivePosOperationSubscribeListenerServiceImpl;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.PosOperationDto;
import ru.diasoft.micro.model.PositionDto;
import ru.diasoft.micro.model.SendChangePositionDto;
import ru.diasoft.micro.service.cache.AgreementService;
import ru.diasoft.micro.service.cache.ClientService;
import ru.diasoft.micro.service.cache.CurrencyService;
import ru.diasoft.micro.service.cache.SecurityService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.*;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Loggable
@RequiredArgsConstructor
public class TransformOperationImpl implements TransformOperation {
    public static final long LONG_VALUE0 = 0L;
    public static final String EMPTY_STRING = "";
    private final ClientService clientService;
    private final AgreementService agreementService;
    private final CurrencyService currencyService;
    private final SecurityService securityService;
    private final PortfolioStructureService portfolioStructureService;

    private final ModelMapper modelMapper;
    private static final DSLogger logger = DSLogManager.getLogger(ReceivePosOperationSubscribeListenerServiceImpl.class);

    public static final int MARGIN_FLAG0 = 0;
    public static final Long FIX_USER_ID_ZERO = Long.valueOf(0);
    public static final Double VALUE_OF_ZERO = Double.valueOf(0);

    @Override
    public Position transformOperationToPosition(PosOperationDto msg) {
        Position positionChange;

        positionChange = modelMapper.map(msg, Position.class);

        positionChange.setIncomeRest(BigDecimal.ZERO);
        Double income = new Double(defineIncome(msg.getDirection(), msg.getQtyAmount()));
        positionChange.setIncome(new BigDecimal(income, MathContext.DECIMAL64));
        positionChange.setExpense(new BigDecimal(defineExpense(msg), MathContext.DECIMAL64));
        positionChange.setOutRest(new BigDecimal(income).subtract(new BigDecimal(defineExpense(msg))));
        positionChange.setPositionType(getPositionType(msg.getOperKind()).getValue());
        /**TODO depoStorageID  сделать метод определния согласно clntpos_depoStorage **/
        positionChange.setPositionDateTime(ZonedDateTime.now());
        positionChange.setPositionDateKind(definePositionDateKind(msg.getFlowsSettleDate(), ZonedDateTime.now()).getValue());
        positionChange.setOperationDate(msg.getFlowsSettleDate().toLocalDateTime().toLocalDate());
        positionChange.setFixFlag(FixFlag.NOT_FIXED.getValue());
        positionChange.setFixUserID(FIX_USER_ID_ZERO);

        positionChange.setClientID(getClientIDByClientCode(msg.getClientCode()));

        positionChange.setAssetID(getAssetIDByAssetCode(msg));

        positionChange.setAgreementID(msg.getAgreementID());

        setAgreementParamByAgreementCode(msg.getClientCode(), positionChange);

        positionChange.setPortfolioStructureID(getPortfolioStructureIDByParam(msg));

        return positionChange;
    }

    @Override
    public Boolean checkOperationDate(ZonedDateTime operationDate, ZonedDateTime currentDate) {

        return (Arrays.stream(PositionDayDiff.values())
                .anyMatch(element -> element.getValue() == getDaysDiff(operationDate, currentDate))) || (getDaysDiff(operationDate, currentDate) > PositionDayDiff.DAYS_T2.getValue());

    }

    @Override
    public SendChangePositionDto transformPositionToDto(List<Position> positionList) {
        SendChangePositionDto sendChangePositionDto = new SendChangePositionDto();
        List<PositionDto> positionDtoList = new LinkedList<>();
        for (Position position:positionList){
            PositionDto positionDto = new PositionDto();

            PortfolioStructure portfolioStructure = portfolioStructureService.findByPortfolioStructureID(position.getPortfolioStructureID());
            if (portfolioStructure!=null){
                positionDto.setBrokAccount(portfolioStructure.getBrokAccount());
                positionDto.setTradePortfolio(portfolioStructure.getTradePortfolio());
                positionDto.setDepoSubAccount(portfolioStructure.getDepoSubAccount());
                positionDto.setClientCode(portfolioStructure.getClientCode());
            }
            else
            {
                positionDto.setBrokAccount(EMPTY_STRING);
                positionDto.setTradePortfolio(EMPTY_STRING);
                positionDto.setDepoSubAccount(EMPTY_STRING);
                positionDto.setClientCode(EMPTY_STRING);
            }


            positionDto.setPositionDateTime(position.getPositionDateTime());
            positionDto.setPositionDateKind(position.getPositionDateKind());
            positionDto.setAgreementID(position.getAgreementID());
            positionDto.setMarket(position.getMarket());
            positionDto.setTradePlace(position.getTradePlace());
            positionDto.setPositionType(position.getPositionType());
            positionDto.setAssetType(position.getAssetType());
            positionDto.setAsset(getAssetCodeIDByAssetID(position));
            positionDto.setIncomeRest(position.getIncomeRest());
            positionDto.setIncome(position.getIncome());
            positionDto.setExpense(position.getExpense());
            positionDto.setOutRest(position.getOutRest());

            positionDtoList.add(positionDto);


        }

        sendChangePositionDto.setPositions(positionDtoList);;

        return sendChangePositionDto;
    }


    private PositionDateKind definePositionDateKind(ZonedDateTime operationDate, ZonedDateTime currentDate) {

        switch (getPositionDayDiff(operationDate, currentDate)) {
            case DAYS_TM_1:
                return PositionDateKind.TMINUS_1;
            case DAYS_T0:
                return PositionDateKind.T0;
            case DAYS_T1:
                return PositionDateKind.T1;
            case DAYS_T2:
                return PositionDateKind.T2;
            default:
                return PositionDateKind.TX;
        }
    }

    private Double defineIncome(Integer direction, Double amount) {
        Double income = VALUE_OF_ZERO;

        if (direction.equals(Direction.RECEIVE.getValue())) {
            income += amount;
        }
        return income;
    }

    private Double defineExpense(PosOperationDto msg) {
        Double expense = VALUE_OF_ZERO;

        if (msg.getDirection().equals(Direction.PAY.getValue())) {
            expense = new Double(msg.getQtyAmount());
        }
        return expense;
    }

    private PositionDayDiff getPositionDayDiff(ZonedDateTime operationDate, ZonedDateTime currentDate) {
        PositionDayDiff resultPositionDayDiff1 = PositionDayDiff.DAYS_TX;
        int days = getDaysDiff(operationDate, currentDate);

        for (PositionDayDiff positionDayDiff : PositionDayDiff.values()) {
            if (days == positionDayDiff.getValue()) {
                resultPositionDayDiff1 = positionDayDiff;
            }
        }

        return resultPositionDayDiff1;
    }

    private int getDaysDiff(ZonedDateTime zonedDateTime1, ZonedDateTime zonedDateTime2) {

        return Period.between(zonedDateTime2.toLocalDate(), zonedDateTime1.toLocalDate()).getDays();
    }

    private Long getClientIDByClientCode(String clientCode) {
        if (clientService.findByExternalNumber(clientCode) == null)
            return LONG_VALUE0;
        else
            return clientService.findByExternalNumber(clientCode).getClientID();
    }

    private Optional<Agreement> getAgreementByAgreementCode(String agreementCode) {

        return Optional.ofNullable(agreementService.findByAgreementCode(agreementCode));
    }

    private void setAgreementParamByAgreementCode(String agreementCode, Position position) {

        Agreement findAgreement = getAgreementByAgreementCode(agreementCode).orElse(null);

        if (findAgreement != null) {

            position.setPortfolioStructureID(findAgreement.getPortfolioID());
        } else {
            position.setPortfolioStructureID(LONG_VALUE0);

        }

    }

    private Long getPortfolioStructureIDByParam(PosOperationDto msg) {

        Map<String, String> portfolioParam = new HashMap<>();

        if (!msg.getTradePortfolio().equals(EMPTY_STRING)){
            portfolioParam.put("tradePortfolio", msg.getTradePortfolio());
        }

        portfolioParam.put("agreementID", msg.getAgreementID().toString());
        portfolioParam.put("tradePlace", msg.getTradePlace().toString());
        portfolioParam.put("clientCode", msg.getClientCode());
        portfolioParam.put("market", msg.getMarket().toString());
        portfolioParam.put("assetType", msg.getAssetType().toString());
        portfolioParam.put("depoSubAccount", msg.getDepoSubAccount());

        Optional<PortfolioStructure> finedPortfolioStructure = portfolioStructureService.findByParam(portfolioParam);
        
        if (finedPortfolioStructure.isPresent()){
            return finedPortfolioStructure.get().getPortfolioStructureID();
        }
        else {
            logger.info("По данной операции порфель не определен: " + msg.toString());
            return LONG_VALUE0;
        }
    }



    private Long getAssetIDByAssetCode(PosOperationDto msg) {

        if (msg.getAssetType().equals(AssetType.MONEY.getValue()))
            return getCurrencyIDByCurrencyBrief(msg.getAsset());
        else if (msg.getAssetType().equals(AssetType.SECURITY.getValue()) || msg.getAssetType().equals(AssetType.DERIVATIVE.getValue()))
            return getSecurityIDByIsin(msg.getAsset());
        else return LONG_VALUE0;
            /**TODO Поддержать
             3 - Драгоценный металл
             5 - Товар**/
    }

    private String getAssetCodeIDByAssetID(Position position) {

        if (position.getAssetType().equals(AssetType.MONEY.getValue()))
            return getCurrencyBriefByCurrencyID(position.getAssetID());
        else if (position.getAssetType().equals(AssetType.SECURITY.getValue()) || position.getAssetType().equals(AssetType.DERIVATIVE.getValue()))
            return getSecurityBriefBySecurityID(position.getAssetID());
        else return EMPTY_STRING;
        /**TODO Поддержать
         3 - Драгоценный металл
         5 - Товар**/
    }


    private Long getCurrencyIDByCurrencyBrief(String currencyBrief) {

        Currency findCurrency = currencyService.findByCurrencyBrief(currencyBrief);

        if (findCurrency==null)
            return LONG_VALUE0;
        else
            return findCurrency.getCurrencyID();
    }

    private String getCurrencyBriefByCurrencyID(Long currencyId) {

        Currency findCurrency = currencyService.findByCurrencyId(currencyId);

        if (findCurrency==null)
            return EMPTY_STRING;
        else
            return findCurrency.getCurrencyBrief();
    }


    private Long getSecurityIDByIsin(String isin) {

        Security findSecurity = securityService.findByIsin(isin);

        if (findSecurity==null)
            return LONG_VALUE0;
        else
            return findSecurity.getSecurityID();
    }

    private String getSecurityBriefBySecurityID(Long securityID) {

        Security findSecurity = securityService.findBySecurityId(securityID);

        if (findSecurity==null)
            return EMPTY_STRING;
        else
            return findSecurity.getSecurityBrief();
    }


    private PositionType getPositionType(Integer operationKind) {

        switch (operationKind) {
            case 10:
                return PositionType.VARIATION_MARGIN;
            case 11:
                return PositionType.GUARANTEE_SECURITY;
            default:
                return PositionType.SIMPLE_POSITION;
        }
    }

}