package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.constants.PositionConst;
import ru.diasoft.micro.domain.BrokAccount;
import ru.diasoft.micro.domain.PortfolioStructure;
import ru.diasoft.micro.domain.Portfolio;
import ru.diasoft.micro.enums.AssetType;
import ru.diasoft.micro.enums.CodeType;
import ru.diasoft.micro.enums.MarketType;
import ru.diasoft.micro.enums.OperationKind;
import ru.diasoft.micro.enums.PositionDateKind;
import ru.diasoft.micro.exception.SearchPortfolioStructureException;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.*;
import ru.diasoft.micro.repository.BrokAccountRepository;
import ru.diasoft.micro.repository.PortfolioRepository;
import ru.diasoft.micro.repository.PortfolioStructureRepository;

import javax.validation.constraints.Null;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Loggable
public class PortfolioStructureServiceImpl implements PortfolioStructureService {

    public static final String PARAM_AGREEMENT_ID = "agreementID";
    public static final String PARAM_TRADE_PORTFOLIO = "tradePortfolio";
    public static final String PARAM_DEPO_SUB_ACCOUNT = "depoSubAccount";
    public static final String PARAM_TRADE_PLACE = "tradePlace";
    public static final String PARAM_CLIENT_CODE = "clientCode";
    public static final String PARAM_MARKET = "market";
    public static final String PARAM_ASSET_TYPE = "assetType";
    public static final String PARAM_BROK_ACCOUNT = "brokAccount";
    public static final String PARAM_OPER_KIND = "operKind";
    public static final String PARAM_CLEARING_PLACE = "clearingPlace";

    public static final String ACCOUNT_LINKTYPE_1_TORG = "1_ТОРГ";
    public static final String MARKET_MOSBIRGA_SR = "Мосбиржа СР";
    public static final String MARKET_MOSBIRGA_FR = "Мосбиржа ФР";

    public static final String settingsTradePortfolio = "#{@tradePortfolioBean}";

    private final PortfolioStructureRepository portfolioStructureRepository;
    private final BrokAccountRepository brokAccountRepository;
    private final PortfolioRepository portfolioRepository;

    private static final DSLogger logger = DSLogManager.getLogger(PortfolioStructureServiceImpl.class);

    @Override
    public Optional<PortfolioStructure> findByParam(Map<String, String> portfolioParam) {
        Optional<PortfolioStructure> portfolioStructure = null;

        logger.info("findPortfolioStructureByParam: " + portfolioParam.toString());

        if (Integer.parseInt(portfolioParam.get(PARAM_ASSET_TYPE))==AssetType.MONEY.getValue()){
        /**Если tradePortfolio и brokAccount передан, анализируется:

         Брокерский счет (brokAccount) = clntpos_portfolio. brokaccount
         Торговый портфель (tradePortfolio) = clntpos_portfolio. tradePortfolio
         <пусто> = clntpos_portfolio. deposubaccounttype.
         <пусто> = clntpos_portfolio. clearingplace*/
         if (portfolioParam.containsKey(PARAM_TRADE_PORTFOLIO)&portfolioParam.containsKey(PARAM_BROK_ACCOUNT)){
             logger.info("findPortfolioStructure PARAM_TRADE_PORTFOLIO PARAM_BROK_ACCOUNT : " + portfolioParam.toString());

              portfolioStructure = portfolioStructureRepository.findByTradePortfolioAndBrokAccountAndDepoSubAccountNullAndClearingPlaceNull(portfolioParam.get(PARAM_TRADE_PORTFOLIO), portfolioParam.get(PARAM_BROK_ACCOUNT));
          }
         /**Брокерский счет (brokAccount) = clntpos_portfolio. brokaccount
          Торговый портфель определяется из настройки = clntpos_portfolio. tradePortfolio
          <пусто> = clntpos_portfolio. deposubaccounttype.
          <пусто> = clntpos_portfolio. clearingplace*/
         else if((!portfolioParam.containsKey(PARAM_TRADE_PORTFOLIO))&portfolioParam.containsKey(PARAM_BROK_ACCOUNT)) {
             logger.info("findPortfolioStructure SETTINGS PARAM_TRADE_PORTFOLIO PARAM_BROK_ACCOUNT : " + portfolioParam.toString());
             portfolioStructure = portfolioStructureRepository.findByTradePortfolioAndBrokAccountAndDepoSubAccountNullAndClearingPlaceNull(settingsTradePortfolio, portfolioParam.get(PARAM_BROK_ACCOUNT));
         }
         else
             {
             /**
              * Если сделка, то анализируется:
              *
              * Место совершения  операции (tradePlace) = clntpos_portfoliostructure. tradeplace
              * Код клиента (clientCode) = clntpos_portfoliostructure. clientCode*/
             if (isDealOperationKind(Integer.parseInt(portfolioParam.get(PARAM_OPER_KIND)))){

                 logger.info("findPortfolioStructure PARAM_TRADE_PLACE PARAM_CLIENT_CODE : " + portfolioParam.toString());
                 portfolioStructure = portfolioStructureRepository.findByTradePlaceAndClientCode(Integer.parseInt(portfolioParam.get(PARAM_TRADE_PLACE)), portfolioParam.get(PARAM_CLIENT_CODE));
             }
             /** Если "Корпоративные события и глобальные операции с ЦБ", то анализируется :
              *
              * ЮДО (agreementID) = clntpos_portfoliostructure. agreementid
              * Раздел счета ДЕПО (depoSubAccount) = clntpos_portfoliostructure. deposubaccount*/
             else if (isDepoOperationKind(Integer.parseInt(portfolioParam.get(PARAM_OPER_KIND)))){

                 logger.info("findPortfolioStructure PARAM_AGREEMENT_ID PARAM_DEPO_SUB_ACCOUNT : " + portfolioParam.toString());

                 portfolioStructure = portfolioStructureRepository.findByBrokerAgreementIDAndDepoSubAccount(Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID)),portfolioParam.get(PARAM_DEPO_SUB_ACCOUNT));

             }
          }
        }
        else /**Если тип актива (assetType) = 2 - Ценная бумага
         ЮДО (agreementID) = clntpos_portfoliostructure. agreementid
         Раздел счета ДЕПО (depoSubAccount) = clntpos_portfoliostructure. deposubaccount
         clearingplace.  Хранилище (депозитарий) приходит с операцией или определяется по счету ДЕПО (depoAccount).*/{
                    logger.info("findPortfolioStructure PARAM_AGREEMENT_ID PARAM_DEPO_SUB_ACCOUNT PARAM_CLEARING_PLACE : " + Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID))+ " " + portfolioParam.get(PARAM_DEPO_SUB_ACCOUNT) + " " + portfolioParam.get(PARAM_CLEARING_PLACE));
            portfolioStructure = portfolioStructureRepository.findByBrokerAgreementIDAndDepoSubAccountAndClearingPlace(Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID)),portfolioParam.get(PARAM_DEPO_SUB_ACCOUNT),portfolioParam.get(PARAM_CLEARING_PLACE));

        }

     return portfolioStructure;
    }

    @Override
    public PortfolioStructure findByPortfolioStructureID(Long portfolioStructureID) {
        return portfolioStructureRepository.findById(portfolioStructureID).orElse(null);
    }


    @Override
    @Transactional
    public List<PortfolioStructure> transformDtoToPortfolioStructure(BrokerAgreementDto brokerAgreementDto) throws SearchPortfolioStructureException {

        List<PortfolioStructure> portfolioStructureList = new ArrayList<>();

        /**проходим по набору связанных счетов для поиска записей с типам кода = торговый код клиента,
         **/
        for (PAPI_SOrd_AftMasPrMDOLinksDto papiSOrdAftMasPrMDOLinksDto:brokerAgreementDto.getPAPI_SOrd_AftMasPrMDOLinks()
                .stream()
                .filter(a->a.getCodeType().equals(CodeType.CODE_TYPE_TORG.getValue())).collect(Collectors.toList())){

                logger.info("papiSOrdAftMasPrMDOLinksDto: " + papiSOrdAftMasPrMDOLinksDto.toString());

                PortfolioStructure portfolioStructure = new PortfolioStructure();

                portfolioStructure.setClientCode(papiSOrdAftMasPrMDOLinksDto.getCodeValue());
                /**определяем из связанного набора общих сведений (связь по идентификтору заявки ServiceOrderID) идентификатор брокерского договора обсулживания*/
                portfolioStructure.setBrokerAgreementID(brokerAgreementDto.getPAPI_SOrd_AftMasProcess()
                        .stream()
                        .filter(p->p.getServiceOrderID().equals(papiSOrdAftMasPrMDOLinksDto.getServiceOrderID()))
                        .findFirst().get().getBrokerAgreementID());

                portfolioStructure.setSubAgreementID(papiSOrdAftMasPrMDOLinksDto.getSubAgreementID());
                portfolioStructure.setTradePlace(PositionConst.marketTradePlaces.get(papiSOrdAftMasPrMDOLinksDto.getMarket()));

                /**определяем из связанного набора Привязки кодов и счетов к рынкам (связь по идентификтору заявки ServiceOrderID
                 * и идентификатору модульного договора) брокерский счет*/
                portfolioStructure.setBrokAccount(brokerAgreementDto.getPAPI_SOrd_AftMasPrMDOLinks()
                        .stream()
                        .filter(a -> a.getSubAgreementID().equals(papiSOrdAftMasPrMDOLinksDto.getSubAgreementID())&a.getCodeType().equals(CodeType.CODE_TYPE_BROK.getValue()))
                        .findFirst()
                        .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                        .getCodeValue());

                /**определяем из связанного набора Привязки кодов и счетов к рынкам (связь по идентификтору заявки ServiceOrderID
                * и идентификатору модульного договора) ПортфельДС*/
                portfolioStructure.setTradePortfolio(brokerAgreementDto.getPAPI_SOrd_AftMasPrMDOLinks()
                        .stream()
                        .filter(a -> a.getSubAgreementID().equals(papiSOrdAftMasPrMDOLinksDto.getSubAgreementID())&a.getCodeType().equals(CodeType.CODE_TYPE_PORTF.getValue()))
                        .findFirst()
                        .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                        .getCodeValue());

                portfolioStructureList.add(portfolioStructure);

                logger.info("portfolioStructure after CODE_TYPE_TORG: " + portfolioStructure.toString());
        }


        /**проходим по набору Правила определения Портфеля для ЦБ для поиска записей
         **/
        for (PAPI_SOrd_AftMasPrRulesDto papiSOrdAftMasPrRulesDto: brokerAgreementDto.getPAPI_SOrd_AftMasPrRules()){

            PortfolioStructure portfolioStructure = new PortfolioStructure();

            portfolioStructure.setBrokAccount(papiSOrdAftMasPrRulesDto.getCodeValue());
            portfolioStructure.setClearingPlace(papiSOrdAftMasPrRulesDto.getClearingPlace());
            portfolioStructure.setTradePortfolio(papiSOrdAftMasPrRulesDto.getPortfolio());
            portfolioStructure.setDepoSubAccountType(papiSOrdAftMasPrRulesDto.getDepoSubAccountType());

            /**определяем из связанного набора Параметры кодов Раздел счета ДЕПО*/
            portfolioStructure.setDepoSubAccount(brokerAgreementDto.getPAPI_SOrd_AftMasPrDEPO()
                    .stream()
                    .filter(d->d.getSubAccountType().equals(papiSOrdAftMasPrRulesDto.getDepoSubAccountType()))
                    .findFirst().get().getNumber());

            /**определяем из связанного набора общих сведений идентификатор брокерского договора */
            portfolioStructure.setBrokerAgreementID(brokerAgreementDto.getPAPI_SOrd_AftMasProcess()
                    .stream()
                    .filter(p->p.getServiceOrderID().equals(papiSOrdAftMasPrRulesDto.getServiceOrderID()))
                    .findFirst().get().getBrokerAgreementID());

            portfolioStructureList.add(portfolioStructure);

            logger.info("portfolioStructure after ACCOUNT_LINKTYPE_1_DEPO: " + portfolioStructure.toString());

        }


        /***из полученного детализированного набора структур портфеля составим и сохраним агрегированный набор портфелей
         * по параметрам Тип счета ДЕПО, Брокерский счет, Место хранения, Порфтель
         * для дальнейшего расчета позиции по идентификатоу портфеля */
        List<Portfolio> portfolioList = portfolioRepository.saveAll(getPortfolio(portfolioStructureList));

        logger.info("portfolioList aftersave: " + portfolioList.toString());

        /**Определяем идентификтаор портфеля для каждой записи расширенной стурктуры портфеля */
        for (PortfolioStructure portfolioStructure: portfolioStructureList) {

            Optional<Portfolio> portfolio = portfolioRepository.findByDepoSubAccountTypeAndClearingPlaceAndBrokAccountAndTradePortfolio(portfolioStructure.getDepoSubAccountType(),
                    portfolioStructure.getClearingPlace(),
                    portfolioStructure.getBrokAccount(),
                    portfolioStructure.getTradePortfolio()
                    );

            portfolioStructure.setPortfolioStructureID(portfolio.get().getPortfolioID());

        }


        logger.info("portfolioStructureList: " + portfolioStructureList.toString());

        return portfolioStructureList;
    }

    /**метод поиска кода с типом БрокСчет*/
    private String getBrokAccount(Collection<PAPI_SOrd_AftMasPrMDOLinksDto> linkedCodeAccountDtoCollection, PortfolioStructure portfolioStructure) throws SearchPortfolioStructureException {

        return linkedCodeAccountDtoCollection.stream()
                .filter(a -> a.getSubAgreementID().equals(portfolioStructure.getSubAgreementID()) & a.getCodeType().equals(CodeType.CODE_TYPE_BROK.getValue()))
                .findFirst()
                .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                .getCodeValue();
    }

    /**метод поиска кода с типом ПортфельДС*/
    private String getTradePortfolio(Collection<PAPI_SOrd_AftMasPrMDOLinksDto> linkedCodeAccountDtoCollection, PortfolioStructure portfolioStructure) throws SearchPortfolioStructureException {

        return linkedCodeAccountDtoCollection.stream()
                .filter(a -> a.getSubAgreementID().equals(portfolioStructure.getSubAgreementID()) & a.getCodeType().equals(CodeType.CODE_TYPE_PORTF.getValue()))
                .findFirst()
                .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                .getCodeValue();
    }


    /**метод поиска типа раздела счета депо*/
    private String getDepoSubAccountType(Collection<PAPI_SOrd_AftMasPrDEPODto> depoAccountDtoCollection, PortfolioStructure portfolioStructure) throws SearchPortfolioStructureException {

        return depoAccountDtoCollection.stream()
                .filter(a -> a.getNumber().equals(portfolioStructure.getDepoSubAccount()))
                .findFirst()
                .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                .getSubAccountType();
    }


    private String getClearingPlace(Collection<PAPI_SOrd_AftMasPrRulesDto> portfolioRuleDtoCollection, PortfolioStructure portfolioStructure) throws SearchPortfolioStructureException {

        return portfolioRuleDtoCollection.stream()
                .filter(rule -> rule.getCodeValue().equals(portfolioStructure.getBrokAccount()) & rule.getDepoSubAccountType().equals(portfolioStructure.getDepoSubAccountType()))
                .findFirst()
                .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                .getClearingPlace();
    }

    private String getTradePortfolioByRule(Collection<PAPI_SOrd_AftMasPrRulesDto> portfolioRuleDtoCollection, PortfolioStructure portfolioStructure) throws SearchPortfolioStructureException {

        return portfolioRuleDtoCollection.stream()
                .filter(rule -> rule.getCodeValue().equals(portfolioStructure.getBrokAccount()) & rule.getDepoSubAccountType().equals(portfolioStructure.getDepoSubAccountType()))
                .findFirst()
                .orElseThrow(()-> new SearchPortfolioStructureException("portfoliostructure.message.error"))
                .getPortfolio();
    }

    @Override
    public List<PortfolioStructure> saveAll(List<PortfolioStructure> portfolioStructureList) {
        return portfolioStructureRepository.saveAll(portfolioStructureList);
    }

    @Override
    public List<BrokAccount> transformDtoToBrokAccount(BrokerAgreementDto brokerAgreementDto) throws SearchPortfolioStructureException {

        List<BrokAccount> brokAccounts = new ArrayList<>();

        /**Параметры кодов БрокСчет*/
        for (PAPI_SOrd_AftMasPrCodesDto papiSOrdAftMasPrCodesDto: brokerAgreementDto.getPAPI_SOrd_AftMasPrCodes()
                .stream()
                .filter(c -> c.getCodeType().equals(CodeType.CODE_TYPE_BROK.getValue()))
                .collect(Collectors.toList())) {

            BrokAccount brokAccount = BrokAccount.builder()
                    .brokAccount(papiSOrdAftMasPrCodesDto.getCodeValue())
                    .agreementID(brokerAgreementDto.getPAPI_SOrd_AftMasProcess()
                            .stream()
                            .filter(p->p.getServiceOrderID().equals(papiSOrdAftMasPrCodesDto.getServiceOrderID()))
                            .findFirst().get().getBrokerAgreementID())
                    .marginLending(papiSOrdAftMasPrCodesDto.getMarginalityLevel())
                    .moneyOvernight(papiSOrdAftMasPrCodesDto.getOvernightMoney())
                    .securityOvernight(papiSOrdAftMasPrCodesDto.getOvernightSecurity())
                    .uccMoEx(papiSOrdAftMasPrCodesDto.getUCCMoEx())
                    .derivAccount(brokerAgreementDto.getPAPI_SOrd_AftMasPrMDOLinks()
                            .stream()
                            .filter(a->a.getServiceOrderID().equals(papiSOrdAftMasPrCodesDto.getServiceOrderID())
                                    &a.getMarket().equals(MARKET_MOSBIRGA_SR)
                                    &a.getCodeType().equals(CodeType.CODE_TYPE_TORG.getValue()))
                            .findFirst().orElse(new PAPI_SOrd_AftMasPrMDOLinksDto())
                            .getCodeValue())
                    .tradingAccount(brokerAgreementDto.getPAPI_SOrd_AftMasPrMDOLinks()
                            .stream()
                            .filter(a->a.getServiceOrderID().equals(papiSOrdAftMasPrCodesDto.getServiceOrderID())
                                    &a.getMarket().equals(MARKET_MOSBIRGA_FR)
                                    &a.getAccountLinkType().equals(ACCOUNT_LINKTYPE_1_TORG))
                            .findFirst()
                            .orElse(new PAPI_SOrd_AftMasPrMDOLinksDto())
                            .getAccountNumber())
                    .build();

            brokAccounts.add(brokAccount);

        }

        return brokAccounts;
    }

    @Override
    public List<BrokAccount> saveAllBrokAccount(List<BrokAccount> brokAccountList) {
        return brokAccountRepository.saveAll(brokAccountList);
    }

    @Override
    public List<Portfolio> saveAllPortfolio(List<Portfolio> portfolioList) {
        return portfolioRepository.saveAll(portfolioList);
    }

    private Set<Portfolio> getPortfolio(List<PortfolioStructure> portfolioStructureList){

        List<Portfolio> portfolioList = new ArrayList<>();

        for (PortfolioStructure portfolioStructure : portfolioStructureList) {

            Optional<Portfolio> existedPortfolio = portfolioRepository.findByDepoSubAccountTypeAndClearingPlaceAndBrokAccountAndTradePortfolio(portfolioStructure.getDepoSubAccountType(),
                    portfolioStructure.getClearingPlace(),
                    portfolioStructure.getBrokAccount(),
                    portfolioStructure.getTradePortfolio());

            if (!existedPortfolio.isPresent()){
                Portfolio portfolio = Portfolio.builder()
                    .brokAccount(portfolioStructure.getBrokAccount())
                    .tradePortfolio(portfolioStructure.getTradePortfolio())
                    .clearingPlace(portfolioStructure.getClearingPlace())
                    .depoSubAccountType(portfolioStructure.getDepoSubAccountType())
                    .build();

                portfolioList.add(portfolio);}
        }

        logger.info("portfolioList after getPortfolio: " + portfolioList.toString());
        logger.info("Set<Portfolio>: " + portfolioList.stream().distinct().collect(Collectors.toSet()).toString());

        return portfolioList.stream().distinct().collect(Collectors.toSet());

    }

    private Boolean isDealOperationKind(Integer operationKind) {

        return Arrays.stream(OperationKind.values())
                .filter(element->element.getType().equals("Deal"))
                .anyMatch(element -> element.getValue()==operationKind);

    }

    private Boolean isDepoOperationKind(Integer operationKind) {

        return Arrays.stream(OperationKind.values())
                .filter(element->element.getType().equals("DepoOperation"))
                .anyMatch(element -> element.getValue()==operationKind);

    }

}
