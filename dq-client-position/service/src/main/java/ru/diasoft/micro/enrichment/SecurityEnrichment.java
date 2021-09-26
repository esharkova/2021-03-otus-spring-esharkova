package ru.diasoft.micro.enrichment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.domain.securityprofiledto.SecuritySearchParamDto;
import ru.diasoft.micro.exception.FeignClientException;
import ru.diasoft.micro.feign.digitalmarketprofile.SecurityProfileClientFacade;
import ru.diasoft.micro.repository.SecurityRepository;
import ru.diasoft.micro.util.LoggerUtils1;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mkushcheva
 * класс обогащения ценной бумаги
 * пока обогащаем по одной ISIN.
 */
@Component
@RequiredArgsConstructor
public class SecurityEnrichment {
    private static final DSLogger logger = DSLogManager.getLogger(SecurityEnrichment.class);
    private final SecurityRepository securityRepository;
    private final SecurityProfileClientFacade securityProfileClientFacade;
    public static final long LONG_VALUE0 = 0L;

    public Security enrich(String isin) {
        LoggerUtils1.writeToLogDebug(logger, "Enrichment Security from service SecurityProfile by isin = {} start", isin);

        try {
            List<Security> securityList = securityProfileClientFacade.getSecuritiesBySearchParam(fillSearchParamForIsinList(isin));
            if (securityList != null && !securityList.isEmpty()) {
                Security securityFind = securityList.get(0);
                saveEnriched(securityFind);
            } else {
                LoggerUtils1.writeToLogInfo(logger, "Ценная бумага по isin {} не определена", isin);
            }
        } catch (FeignClientException e) {
            LoggerUtils1.writeToLogInfo(logger, "Service call error Security Profile: {} ", e);
        }

        return Security.builder().securityID(LONG_VALUE0).build();
    }

    public void enrich(List<Long> idList) {
        LoggerUtils1.writeToLogDebug(logger, "Enrichment Security from service SecurityProfile by securityIDs = {} start", idList);
        try {
            List<Security> securityList = securityProfileClientFacade.getSecuritiesBySearchParam(fillSearchParamForIdList(idList));
            saveEnriched(securityList);
        } catch (FeignClientException e) {
            LoggerUtils1.writeToLogInfo(logger, "Service call error Security Profile: {} ", e);
        }
    }

    private SecuritySearchParamDto fillSearchParamForIdList(List<Long> idList) {
        return SecuritySearchParamDto.builder()
                .securityIds(idList)
                .build();
    }

    private SecuritySearchParamDto fillSearchParamForIsinList(String isin) {
        List<String> isins = new LinkedList<>();
        isins.add(isin);
        return SecuritySearchParamDto.builder()
                .isinCodes(isins)
                .build();
    }

    private void saveEnriched(Security security) {
        LoggerUtils1.writeToLogDebug(logger, "Save enriched Security: {} ", security);
        securityRepository.save(security);
    }

    private void saveEnriched(List<Security> securitys) {
        LoggerUtils1.writeToLogDebug(logger, "Save enriched Securitys: {} ", securitys);
        //удалиим все бумаги. так как не использующиеся в позиции нужно удалить, а существующие обновить.
        //зальем просто заново нужные
        securityRepository.deleteAll();
        securityRepository.saveAll(securitys);
    }
}
