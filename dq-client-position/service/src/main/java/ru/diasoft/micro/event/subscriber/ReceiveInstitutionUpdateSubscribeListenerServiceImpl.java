package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.receiveinstitutionupdate.subscribe.ReceiveInstitutionUpdateSubscribeListenerService;
import ru.diasoft.micro.model.InstitutionUpdateDto;
import ru.diasoft.micro.service.cache.AgreementService;
import ru.diasoft.micro.service.cache.ClientServiceImpl;


@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveInstitutionUpdateSubscribeListenerServiceImpl implements ReceiveInstitutionUpdateSubscribeListenerService {
    private final ClientServiceImpl clientService;

    private final AgreementService agreementService;

    @Override
    public void receiveInstitutionUpdate(InstitutionUpdateDto msg){
        Client client = ClientMapper.INSTANCE.InstitutionUpdateDtoToClient(msg);
        clientService.save(client);

        agreementService.updateAgreementClient(client);

    }
}
