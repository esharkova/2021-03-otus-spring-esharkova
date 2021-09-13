package ru.diasoft.micro.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.repository.AgreementRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AgreementServiceImpl implements AgreementService {
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
}
