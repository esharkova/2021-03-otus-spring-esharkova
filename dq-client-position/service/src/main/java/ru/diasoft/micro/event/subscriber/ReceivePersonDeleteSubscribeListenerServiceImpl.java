package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.PersonDeleteDto;
import ru.diasoft.micro.receivepersondelete.subscribe.ReceivePersonDeleteSubscribeListenerService;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceivePersonDeleteSubscribeListenerServiceImpl implements ReceivePersonDeleteSubscribeListenerService {
    private final ClientServiceImpl clientService;

    @Override
    public void receivePersonDelete(PersonDeleteDto msg) {
        Client client = ClientMapper.INSTANCE.PersonDeleteDtoToClient(msg);
        clientService.delete(client.getClientID());
    }
}
