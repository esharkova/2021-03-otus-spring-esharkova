package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.PortfolioStructure;

import java.util.ArrayList;
import java.util.List;

public class PortfolioStructureGenerator {
    public static final long ID = 1;
    public static final String TRADEPORTFOLIO = "tradePortfolio";
    public static final String BROK_ACCOUNT = "brokAccount";
    public static final String CLIENT_CODE = "clientCode";
    public static final String DEPO_SUB_ACCOUNT = "depoSubAccount";
    public static final int MARKET = 1;
    public static final int PRIORITY = 1;
    public static final String SEC_SETT_ACCOUNT = "secSettAccount";
    public static final int TRADE_PLACE = 1;

    public static final long ID_FORACCOUNTFUT = 1;
    public static final String CLIENT_CODE_FORACCOUNTFUT = "ACCOUNTFUT";
    public static final int TRADE_PLACE_FORACCOUNTFUT = 1;
    public static final int MARKET_FORACCOUNTFUT = 2;

    public static PortfolioStructure getPortfolioStructure(){
        return PortfolioStructure.builder()
                .portfolioStructureID(ID)
                .tradePortfolio(TRADEPORTFOLIO)
                .brokerAgreementID(AgreementGenerator.AGREEMENT_ID)
                .brokAccount(BROK_ACCOUNT)
                .clientCode(CLIENT_CODE)
                .depoSubAccount(DEPO_SUB_ACCOUNT)
                .market(MARKET)
                .priority(PRIORITY)
                .secSettAccount(SEC_SETT_ACCOUNT)
                .tradePlace(TRADE_PLACE)
                .build();
    }

    public static List<PortfolioStructure> getPortfolioStructures(){
        List<PortfolioStructure> portfolioStructureList = new ArrayList<>();
        portfolioStructureList.add(PortfolioStructure.builder()
                .portfolioStructureID(ID)
                .tradePortfolio(TRADEPORTFOLIO)
                .brokerAgreementID(AgreementGenerator.AGREEMENT_ID)
                .brokAccount(BROK_ACCOUNT)
                .clientCode(CLIENT_CODE)
                .depoSubAccount(DEPO_SUB_ACCOUNT)
                .market(MARKET)
                .priority(PRIORITY)
                .secSettAccount(SEC_SETT_ACCOUNT)
                .tradePlace(TRADE_PLACE)
                .clearingPlace("НРД")
                .build());

        portfolioStructureList.add(PortfolioStructure.builder()
                .portfolioStructureID(ID_FORACCOUNTFUT)
                .tradePortfolio(TRADEPORTFOLIO)
                .brokerAgreementID(AgreementGenerator.AGREEMENT_ID)
                .brokAccount(BROK_ACCOUNT)
                .clientCode(CLIENT_CODE_FORACCOUNTFUT)
                .depoSubAccount(DEPO_SUB_ACCOUNT)
                .market(MARKET_FORACCOUNTFUT)
                .priority(PRIORITY)
                .secSettAccount(SEC_SETT_ACCOUNT)
                .tradePlace(TRADE_PLACE_FORACCOUNTFUT)
                .clearingPlace("БЭБ")
                .build());

        return portfolioStructureList;
    }

}
