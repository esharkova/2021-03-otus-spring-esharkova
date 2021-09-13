package ru.diasoft.micro.constants;

import com.querydsl.core.types.Path;
import ru.diasoft.micro.domain.QDQCLNTPOSCustomPositions;

import java.util.HashMap;

public class PositionConst {
    private PositionConst() {
    }

    public static final HashMap<String, Path<?>> globalSearchAttributes = new HashMap<>();

    static {
        globalSearchAttributes.put("positiondatekind", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.positionDateKind);
        globalSearchAttributes.put("clientattr", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.clientAttr);
        globalSearchAttributes.put("agreementnumber", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.agreementNumber);
        globalSearchAttributes.put("accountclient", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.accountClient);
        globalSearchAttributes.put("accountfut", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.accountFut);
        globalSearchAttributes.put("portfoliobrief", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.portfolioBrief);
        globalSearchAttributes.put("tradingacc", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.tradingAcc);
        globalSearchAttributes.put("depostoragelocation", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.depoStorageLocation);
        globalSearchAttributes.put("instrumentname", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.instrumentName);
        globalSearchAttributes.put("assettype", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.assetType);
        globalSearchAttributes.put("fininstrumentcode", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.finInstrumentCode);
        globalSearchAttributes.put("isin", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.isin);
        globalSearchAttributes.put("incomerest", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.incomeRest);
        globalSearchAttributes.put("outrest", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.outRest);
        globalSearchAttributes.put("income", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.income);
        globalSearchAttributes.put("expense", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.expense);
        globalSearchAttributes.put("customid", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.customID);
        globalSearchAttributes.put("moneyovernightkind", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.moneyOvernightKind);
        globalSearchAttributes.put("securityovernightkind", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.securityOvernightKind);
        globalSearchAttributes.put("marginlendingkind", QDQCLNTPOSCustomPositions.dQCLNTPOSCustomPositions.marginLendingKind);
    }

    public static final String MAINFILTERNAME = "positionDateKind";
}