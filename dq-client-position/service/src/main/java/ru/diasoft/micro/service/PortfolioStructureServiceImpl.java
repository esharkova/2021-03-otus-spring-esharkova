package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.PortfolioStructure;
import ru.diasoft.micro.enums.AssetType;
import ru.diasoft.micro.enums.MarketType;
import ru.diasoft.micro.repository.PortfolioStructureRepository;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PortfolioStructureServiceImpl implements PortfolioStructureService {

    public static final String PARAM_AGREEMENT_ID = "agreementID";
    public static final String PARAM_TRADE_PORTFOLIO = "tradePortfolio";
    public static final String PARAM_DEPO_SUB_ACCOUNT = "depoSubAccount";
    public static final String PARAM_TRADE_PLACE = "tradePlace";
    public static final String PARAM_CLIENT_CODE = "clientCode";
    public static final String PARAM_MARKET = "market";
    public static final String PARAM_ASSET_TYPE = "assetType";
    private final PortfolioStructureRepository portfolioStructureRepository;

    @Override
    public Optional<PortfolioStructure> findByParam(Map<String, String> portfolioParam) {

        Optional<PortfolioStructure> portfolioStructure;

        if (portfolioParam.containsKey(PARAM_TRADE_PORTFOLIO)){
            portfolioStructure = portfolioStructureRepository.findByAgreementIDAndTradePortfolio(Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID)), portfolioParam.get(PARAM_TRADE_PORTFOLIO));
        }
        else{
            if (portfolioParam.get(PARAM_MARKET) == MarketType.OTCMARKET.getValue()) {
                if (Integer.parseInt(portfolioParam.get(PARAM_ASSET_TYPE))== AssetType.SECURITY.getValue())
                    portfolioStructure = portfolioStructureRepository.findByAgreementIDAndDepoSubAccount(Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID)),portfolioParam.get(PARAM_DEPO_SUB_ACCOUNT));
                else
                    portfolioStructure = portfolioStructureRepository.findByAgreementIDAndTradePlaceAndDepoSubAccount(Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID)),portfolioParam.get(PARAM_TRADE_PLACE), portfolioParam.get(PARAM_DEPO_SUB_ACCOUNT));
            }
            else
                portfolioStructure = portfolioStructureRepository.findByAgreementIDAndClientCode(Long.parseLong(portfolioParam.get(PARAM_AGREEMENT_ID)),portfolioParam.get(PARAM_CLIENT_CODE));
        }
     return portfolioStructure;
    }

    @Override
    public PortfolioStructure findByPortfolioStructureID(Long portfolioStructureID) {
        return portfolioStructureRepository.findById(portfolioStructureID).orElse(null);
    }
}
