package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.generator.LegalsGenerator;
import ru.diasoft.micro.model.LegalDeleteDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReceiveLegalDeleteSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ReceiveLegalDeleteSubscribeListenerServiceImpl deleteSubscribeListenerService;

    @Test
    void recieveLegalDeleteTest() {
        LegalDeleteDto legalDeleteDto = LegalsGenerator.getLegalDeleteDto();
        deleteSubscribeListenerService.receiveLegalDelete(legalDeleteDto);
        verify(clientService, times(1)).delete(legalDeleteDto.getLegalID());
    }
}