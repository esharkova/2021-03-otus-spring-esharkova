package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.LegalDeleteDto;
import ru.diasoft.micro.receivelegaldelete.subscribe.ReceiveLegalDeleteSubscribeListenerService;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveLegalDeleteSubscribeListenerServiceImpl implements ReceiveLegalDeleteSubscribeListenerService {
    private final ClientServiceImpl clientService;

    @Override
    public void receiveLegalDelete(LegalDeleteDto msg) {
        Client client = ClientMapper.INSTANCE.LegalDeleteDtoToClient(msg);
        clientService.delete(client.getClientID());
    }
}
