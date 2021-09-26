package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.receiveinstitutiondelete.subscribe.ReceiveInstitutionDeleteSubscribeListenerService;
import ru.diasoft.micro.model.InstitutionDeleteDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;


@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveInstitutionDeleteSubscribeListenerServiceImpl implements ReceiveInstitutionDeleteSubscribeListenerService {
     private final ClientServiceImpl clientService;

    @Override
    public void receiveInstitutionDelete(InstitutionDeleteDto msg) {
        Client client = ClientMapper.INSTANCE.InstitutionDeleteDtoToClient(msg);
        clientService.delete(client.getClientID());
    }
}
