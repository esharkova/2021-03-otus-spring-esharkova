package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.DQCLNTPOSCustomPositions;
import ru.diasoft.micro.domain.Position;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomPositionGenerator {
    public static final Long CUSTOMID1 = 1L;
    public static final Long CUSTOMID2 = 2L;

    public static final String TRADING_ACC_VALUE = "tradingAccCur";
    public static final String TRADING_ACC2_VALUE = "tradingAcc2";
    public static final Integer POSDATEKINDT0_VALUE = 1;
    public static final Integer POSDATEKINDT1_VALUE = 2;
    public static final Integer ASSETTYPE_CUR = 1;
    public static final Integer ASSETTYPE_SEC = 2;
    public static final Integer ASSETTYPE_DER = 4;
    public static final String ASSETTYPE_CUR_VALUE = "ДС";
    public static final String ASSETTYPE_DER_VALUE = "Ср. инст.";

    public static final String CUSTODY_NRD = "НРД";
    public static final String CUSTODY_BB = "БЭБ";

    public static final Integer DEPOACCTYPE_MAIN = 1;
    public static final Integer DEPOACCTYPE_TRADE = 2;

    public static List<DQCLNTPOSCustomPositions> getDefaultCustomPosition() {
        List<DQCLNTPOSCustomPositions> result = new ArrayList<>();

        result.add(DQCLNTPOSCustomPositions.builder()
                .customID(CUSTOMID1)
                .positionDateKind(POSDATEKINDT0_VALUE)
                .clientAttr("clientAttr")
                .agreementNumber("agreementNumber")
                .accountClient("accountClient")
                .accountFut("accountFut")
                .portfolioBrief("portfolioBrief")
                .tradingAcc("tradingAcc")
                .depoStorageLocation("depoStorageLocation")
                .instrumentName("instrumentName")
                .finInstrumentCode("finIstrumentCode")
                .isin("isin")
                .incomeRest(new BigDecimal(10000))
                .outRest(new BigDecimal(10000))
                .income(new BigDecimal(10000))
                .expense(new BigDecimal(10000))
                .build());

        result.add(DQCLNTPOSCustomPositions.builder()
                .customID(CUSTOMID1)
                .positionDateKind(POSDATEKINDT1_VALUE)
                .clientAttr("clientAttr")
                .agreementNumber("agreementNumber")
                .accountClient("accountClient")
                .accountFut("accountFut")
                .portfolioBrief("portfolioBrief")
                .tradingAcc("tradingAcc")
                .depoStorageLocation("depoStorageLocation")
                .instrumentName("instrumentName")
                .finInstrumentCode("finIstrumentCode")
                .isin("isin")
                .incomeRest(new BigDecimal(10000))
                .outRest(new BigDecimal(10000))
                .income(new BigDecimal(10000))
                .expense(new BigDecimal(10000))
                .build());

        result.add(DQCLNTPOSCustomPositions.builder()
                .customID(CUSTOMID2)
                .positionDateKind(POSDATEKINDT0_VALUE)
                .clientAttr("clientAttr")
                .agreementNumber("agreementNumber")
                .accountClient("accountClient")
                .accountFut("accountFut")
                .portfolioBrief("portfolioBrief")
                .tradingAcc("tradingAcc")
                .depoStorageLocation("depoStorageLocation")
                .instrumentName("instrumentName")
                .finInstrumentCode("finIstrumentCode")
                .isin("isin")
                .incomeRest(new BigDecimal(10000))
                .outRest(new BigDecimal(10000))
                .income(new BigDecimal(10000))
                .expense(new BigDecimal(10000))
                .build());
        return result;
    }

    public static List<Position> getPositionListForCustomPositionTest() {
        List<Position> result = new ArrayList<>();

        result.add(
                Position.builder()
                        .expense(new BigDecimal(15))
                        .positionDateKind(POSDATEKINDT0_VALUE)
                        .fixFlag(0)
                        .positionType(1)
                        .agreementID(AgreementGenerator.AGREEMENT_ID)
                        .clientID(ClientGenerator.CLIENT_ID)
                        .assetID(CurrencyGenerator.CURRENCY_ID)
                        .assetType(ASSETTYPE_CUR) //1 - ДС
                        .portfolioStructureID(PortfolioStructureGenerator.ID)
                        .custody(CUSTODY_NRD)
                        .depoAccType(DEPOACCTYPE_MAIN)
                        .build()
        );
        result.add(
                Position.builder()
                        .expense(new BigDecimal(100))
                        .positionDateKind(POSDATEKINDT0_VALUE)
                        .fixFlag(0)
                        .positionType(2)
                        .agreementID(AgreementGenerator.AGREEMENT_ID)
                        .clientID(ClientGenerator.CLIENT_ID)
                        .assetID(SecurityGenerator.SECURITY_ID)
                        .assetType(ASSETTYPE_SEC) //2 - ЦБ
                        .portfolioStructureID(PortfolioStructureGenerator.ID)
                        .custody(CUSTODY_BB)
                        .depoAccType(DEPOACCTYPE_MAIN)
                        .build()
        );
        result.add(
                Position.builder()
                        .expense(new BigDecimal(20))
                        .positionDateKind(POSDATEKINDT1_VALUE)
                        .fixFlag(0)
                        .assetType(ASSETTYPE_DER) //4 - Дериватив
                        .portfolioStructureID(PortfolioStructureGenerator.ID)
                        .custody(CUSTODY_NRD)
                        .depoAccType(DEPOACCTYPE_TRADE)
                        .build()
        );
        return result;
    }
}
