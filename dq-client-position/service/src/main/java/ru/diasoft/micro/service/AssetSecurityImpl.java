package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.domain.Currency;
import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.enrichment.SecurityEnrichment;
import ru.diasoft.micro.enums.AssetType;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.service.cache.CurrencyService;
import ru.diasoft.micro.service.cache.SecurityService;
import ru.diasoft.micro.util.LoggerUtils1;

@RequiredArgsConstructor
@Loggable
@Service
public class AssetSecurityImpl implements AssetSecurity {
    private static final DSLogger logger = DSLogManager.getLogger(AssetSecurityImpl.class);
    private final SecurityService securityService;
    private final CurrencyService currencyService;
    private final SecurityEnrichment securityEnrichment;

    public static final long LONG_VALUE0 = 0L;

    @Override
    public Long getAssetIDByAssetCode(String assetCode, AssetType assetType) {
        switch (assetType) {
            case SECURITY:
                return getSecurityIDByIsin(assetCode);
            case MONEY:
                return getCurrencyIDByCurrencyBrief(assetCode);
            default:
                return LONG_VALUE0;
        }
    }

    private Long getSecurityIDByIsin(String isin) {
        Long securityID = getSecurityIDByIsinFromCash(isin);
        if (securityID.equals(LONG_VALUE0)) {
            securityID = getSecurityIDByIsinFromSecurityProfile(isin);
        }
        return securityID;
    }

    private Long getSecurityIDByIsinFromCash(String isin) {
        LoggerUtils1.writeToLogDebug(logger, "getSecurityIDByIsinFromCash start");
        return getSecurityIDFromSecurity(securityService.findByIsin(isin));
    }

    private Long getSecurityIDByIsinFromSecurityProfile(String isin) {
        LoggerUtils1.writeToLogDebug(logger, "getSecurityIDByIsinFromSecurityProfile start");
        return getSecurityIDFromSecurity(securityEnrichment.enrich(isin));
    }

    private Long getSecurityIDFromSecurity(Security security) {
        LoggerUtils1.writeToLogDebug(logger, "Found security:: {} ", security);
        if (security != null) {
            return security.getSecurityID();
        }
        return LONG_VALUE0;
    }

    private Long getCurrencyIDByCurrencyBrief(String currencyBrief) {
        LoggerUtils1.writeToLogDebug(logger, "getCurrencyIDByCurrencyBrief start");
        Currency findCurrency = currencyService.findByCurrencyBrief(currencyBrief);

        if (findCurrency == null)
            return LONG_VALUE0;
        else
            return findCurrency.getCurrencyID();
    }
}
