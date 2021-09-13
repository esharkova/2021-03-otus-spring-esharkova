package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.Agreement;

public class AgreementGenerator {

    public static final String AGREEMENT_CODE = "agreementCode";
    public static final String BR_AGR_NUMBER = "brAgrNumber";
    public static final String BROK_ACC_FOND_NUMBER = "brokAccFondNumber";
    public static final String BROK_ACC_FUT_NUMBER = "brokAccFutNumber";
    public static final String FUT_ACC_NUMBER = "futAccNumber";
    public static final int MARGIN_LENDING_ONE = 1;
    public static final int MONEY_OVERNIGHT_ONE = 1;
    public static final int SECURITY_OVERNIGHT = 1;
    public static final long AGREEMENT_ID = 1;

    public static final long CLIENT_ID = 1;
    public static final int CLIENT_TYPE = 1;
    public static final String CLIENT_NAME = "ClientName";

    public static final String TRADING_ACC2_VALUE = "tradingAcc2";

    public static Agreement getAgreement(){
        return Agreement.builder()
                .agreementID(AGREEMENT_ID)
                .agreementCode(AGREEMENT_CODE)
                .brAgrNumber(BR_AGR_NUMBER)
                .brokAccFondNumber(BROK_ACC_FOND_NUMBER)
                .brokAccFutNumber(BROK_ACC_FUT_NUMBER)
                .futAccNumber(FUT_ACC_NUMBER)
                .marginLending(MARGIN_LENDING_ONE)
                .moneyOvernight(MONEY_OVERNIGHT_ONE)
                .securityOvernight(SECURITY_OVERNIGHT)
                .clientID(CLIENT_ID)
                .clientType(CLIENT_TYPE)
                .clientName(CLIENT_NAME)
                .tradingAccBrief(TRADING_ACC2_VALUE)
                .build();
    }
}
