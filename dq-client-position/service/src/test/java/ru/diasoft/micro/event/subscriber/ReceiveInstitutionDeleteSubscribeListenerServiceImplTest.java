package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.generator.InstitutionsGenerator;
import ru.diasoft.micro.model.InstitutionDeleteDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReceiveInstitutionDeleteSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ReceiveInstitutionDeleteSubscribeListenerServiceImpl deleteSubscribeListenerService;

    @Test
    void recieveInstitutionDeleteTest() {
        InstitutionDeleteDto institutionDeleteDto = InstitutionsGenerator.getInstitutionDeleteDto();
        deleteSubscribeListenerService.receiveInstitutionDelete(institutionDeleteDto);
        verify(clientService, times(1)).delete(institutionDeleteDto.getInstitutionID());
    }
}