package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.LegalInsertDto;
import ru.diasoft.micro.receivelegalinsert.subscribe.ReceiveLegalInsertSubscribeListenerService;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveLegalInsertSubscribeListenerServiceImpl implements ReceiveLegalInsertSubscribeListenerService {
    private final ClientServiceImpl clientService;

    @Override
    public void receiveLegalInsert(LegalInsertDto msg) {
        Client client = ClientMapper.INSTANCE.LegalInsertDtoToClient(msg);
        clientService.save(client);
    }
}
