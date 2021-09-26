package ru.diasoft.micro.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.model.BrokerAgreementDto;
import ru.diasoft.micro.model.PAPI_SOrd_AftMasPrMDOLinksDto;
import ru.diasoft.micro.model.PAPI_SOrd_AftMasProcessDto;
import ru.diasoft.micro.repository.AgreementRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgreementServiceImpl implements AgreementService {
    public static final String TRADING_ACC_BRIEF = "1_ТОРГ";
    public static final String TRADING_SYSTEM_FR = "Мосбиржа ФР";
    private final AgreementRepository agreementRepository;

    @Override
    @Cacheable(cacheNames="agreement",key="{#agreementCode}")
    public Agreement findByAgreementCode(String agreementCode) {
        return agreementRepository.findByAgreementCode(agreementCode).orElse(null);
    }

    @Override
    @CacheEvict("agreement")
    public void delete(Long agreementID) {
        agreementRepository.deleteById(agreementID);

    }

    @Override
    @CachePut("agreement")
    public Agreement save(Agreement agreement) {
        return agreementRepository.save(agreement);
    }

    @Override
    public List<Agreement> saveall(List<Agreement> agreementList) {
        return agreementRepository.saveAll(agreementList);
    }

    @Override
    @Cacheable(cacheNames="agreement",key="{#clientID}")
    public Agreement findByClientID(Long clientID) {
        return agreementRepository.findByClientID(clientID).orElse(null);
    }

    @Override
    public void updateAgreementClient(Client client) {

        Optional<Agreement> findAgreement = agreementRepository.findByClientID(client.getClientID());

        if (findAgreement.isPresent()) {
            findAgreement.get().setClientType(client.getClientType());
            findAgreement.get().setClientName(client.getName());

            agreementRepository.save(findAgreement.get());
        }
    }

    @Override
    public List<Agreement> transformDtoToAgreement(BrokerAgreementDto brokerAgreementDto) {
        List<Agreement> agreementList = new ArrayList<>();

        /**из набора Набор общих сведений о юридическом договоре обслуживания выделим договора*/
        for (PAPI_SOrd_AftMasProcessDto papiSOrdAftMasProcessDto: brokerAgreementDto.getPAPI_SOrd_AftMasProcess()) {

            Agreement agreement = Agreement.builder()
                    .agreementID(papiSOrdAftMasProcessDto.getBrokerAgreementID())
                    .agreementCode(papiSOrdAftMasProcessDto.getBrAgrNumber())
                    .brAgrNumber(papiSOrdAftMasProcessDto.getBrAgrNumber())
                    /**для поиска счета 1_торг выделим коллекцию для обрабатываемой заявки и там будем искать счет*/
                    /*.tradingAccBrief(getTradingAcc((Collection<PAPI_SOrd_AftMasPrMDOLinksDto>) brokerAgreementDto.getPAPI_SOrd_AftMasPrMDOLinks().stream()
                            .filter(s->s.getServiceOrderID().equals(papiSOrdAftMasProcessDto.getServiceOrderID()))))*/
                    .clientID(papiSOrdAftMasProcessDto.getClientID())
                    .clientType(papiSOrdAftMasProcessDto.getClientType())
                    .clientName(papiSOrdAftMasProcessDto.getClientName())
                    .build();

            agreementList.add(agreement);
        }

        return agreementList;
    }

    private String getTradingAcc(Collection<PAPI_SOrd_AftMasPrMDOLinksDto> papiSOrdAftMasPrMDOLinksDtos) {

        return papiSOrdAftMasPrMDOLinksDtos.stream()
                .filter(a->a.getAccountLinkType().equals(TRADING_ACC_BRIEF)&a.getMarket().equals(TRADING_SYSTEM_FR))
                .findFirst().get().getAccountNumber();

    }
}
