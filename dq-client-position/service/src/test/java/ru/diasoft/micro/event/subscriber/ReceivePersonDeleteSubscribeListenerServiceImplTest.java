package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.generator.PersonsGenerator;
import ru.diasoft.micro.model.PersonDeleteDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReceivePersonDeleteSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ReceivePersonDeleteSubscribeListenerServiceImpl deleteSubscribeListenerService;

    @Test
    void recievePersonDeleteTest() {
        PersonDeleteDto personDeleteDto = PersonsGenerator.getPersonDeleteDto();
        deleteSubscribeListenerService.receivePersonDelete(personDeleteDto);
        verify(clientService, times(1)).delete(personDeleteDto.getPersonID());
    }
}