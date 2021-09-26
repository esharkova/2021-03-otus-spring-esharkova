package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.PersonUpdateDto;
import ru.diasoft.micro.receivepersonupdate.subscribe.ReceivePersonUpdateSubscribeListenerService;
import ru.diasoft.micro.service.cache.AgreementService;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceivePersonUpdateSubscribeListenerServiceImpl implements ReceivePersonUpdateSubscribeListenerService {
    private final ClientServiceImpl clientService;
    private final AgreementService agreementService;

    @Override
    public void receivePersonUpdate(PersonUpdateDto msg) {
        Client client = ClientMapper.INSTANCE.personUpdateDtoToClient(msg);
        clientService.save(client);

        agreementService.updateAgreementClient(client);

    }
}
