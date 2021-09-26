package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.exception.SearchPortfolioStructureException;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.BrokerAgreementDto;
import ru.diasoft.micro.receivebrokeragreementinsert.subscribe.ReceiveBrokerAgreementInsertSubscribeListenerService;
import ru.diasoft.micro.service.PortfolioStructureService;
import ru.diasoft.micro.service.cache.AgreementService;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveBrokerAgreementInsertSubscribeListenerServiceImpl implements ReceiveBrokerAgreementInsertSubscribeListenerService {
    private final AgreementService agreementService;
    private final PortfolioStructureService portfolioStructureService;

    private static final DSLogger logger = DSLogManager.getLogger(ReceiveBrokerAgreementInsertSubscribeListenerServiceImpl.class);

    @Override
    @Transactional
    public void receiveBrokerAgreementInsert(BrokerAgreementDto msg) {

        try {
            agreementService.saveall(agreementService.transformDtoToAgreement(msg));
            portfolioStructureService.saveAll(portfolioStructureService.transformDtoToPortfolioStructure(msg));
            portfolioStructureService.saveAllBrokAccount(portfolioStructureService.transformDtoToBrokAccount(msg));
        }
        catch (SearchPortfolioStructureException e) {
            logger.error(e.getMessage(), e);
        }

    }
}
