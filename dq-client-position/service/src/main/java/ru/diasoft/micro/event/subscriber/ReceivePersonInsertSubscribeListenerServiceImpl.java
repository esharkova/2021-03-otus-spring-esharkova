package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.PersonInsertDto;
import ru.diasoft.micro.receivepersoninsert.subscribe.ReceivePersonInsertSubscribeListenerService;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceivePersonInsertSubscribeListenerServiceImpl implements ReceivePersonInsertSubscribeListenerService {
    private final ClientServiceImpl clientService;

    @Override
    public void receivePersonInsert(PersonInsertDto msg) {
        Client client = ClientMapper.INSTANCE.personInsertDtoToClient(msg);
        clientService.save(client);
    }
}
