package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.generator.ClientGenerator;
import ru.diasoft.micro.generator.PersonsGenerator;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.PersonInsertDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceivePersonInsertSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ReceivePersonInsertSubscribeListenerServiceImpl insertSubscribeListenerService;

    @Test
    void recievePersonInsertTest() {
        Client client = ClientGenerator.getClientPerson();
        when(clientService.save(any())).thenReturn(client);

        PersonInsertDto personInsertDto = PersonsGenerator.getPersonInsertDto();
        insertSubscribeListenerService.receivePersonInsert(personInsertDto);
        Client insertDtoToClient = ClientMapper.INSTANCE.personInsertDtoToClient(personInsertDto);

        assertThat(insertDtoToClient).isEqualTo(client);
        verify(clientService, times(1)).save(client);
    }
}