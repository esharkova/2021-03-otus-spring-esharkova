package ru.diasoft.micro.service.cache;

import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.model.BrokerAgreementDto;

import java.util.List;
import java.util.Optional;

public interface AgreementService {

    Agreement findByAgreementCode(String agreementCode);

    void delete(Long agreementID);

    Agreement save(Agreement agreement);

    List<Agreement> saveall(List<Agreement> agreementList);

    Agreement findByClientID(Long clientID);

    void updateAgreementClient(Client client);

    List<Agreement> transformDtoToAgreement(BrokerAgreementDto brokerAgreementDto);
}
