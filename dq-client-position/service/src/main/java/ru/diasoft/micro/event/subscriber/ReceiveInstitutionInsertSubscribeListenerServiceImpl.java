package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.receiveinstitutioninsert.subscribe.ReceiveInstitutionInsertSubscribeListenerService;
import ru.diasoft.micro.model.InstitutionInsertDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveInstitutionInsertSubscribeListenerServiceImpl implements ReceiveInstitutionInsertSubscribeListenerService {
    private final ClientServiceImpl clientService;

    @Override
    public void receiveInstitutionInsert(InstitutionInsertDto msg){
        Client client = ClientMapper.INSTANCE.InstitutionInsertDtoToClient(msg);
        clientService.save(client);
    }
}
