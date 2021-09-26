package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.BrokAccount;

public class BrokAccountGenerator {
    public static final String DERIV_ACCOUNT = "derivAccount";
    public static final String TRADING_ACCOUNT = "tradingAccount";
    public static final int MARGIN_LENDING_ONE = 1;
    public static final int MONEY_OVERNIGHT_ONE = 1;
    public static final int SECURITY_OVERNIGHT = 1;

    public static BrokAccount getBrokAccount() {
        return BrokAccount.builder()
                .agreementID(1L)
                .brokAccount(PortfolioStructureGenerator.BROK_ACCOUNT)
                .derivAccount(DERIV_ACCOUNT)
                .tradingAccount(TRADING_ACCOUNT)
                .moneyOvernight(MONEY_OVERNIGHT_ONE)
                .securityOvernight(SECURITY_OVERNIGHT)
                .marginLending(MARGIN_LENDING_ONE)
                .build();
    }
}
