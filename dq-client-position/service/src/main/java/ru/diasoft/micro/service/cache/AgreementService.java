package ru.diasoft.micro.service.cache;

import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;

import java.util.Optional;

public interface AgreementService {

    Agreement findByAgreementCode(String agreementCode);

    void delete(Long agreementID);

    Agreement save(Agreement agreement);

    Agreement findByClientID(Long clientID);

    void updateAgreementClient(Client client);
}
