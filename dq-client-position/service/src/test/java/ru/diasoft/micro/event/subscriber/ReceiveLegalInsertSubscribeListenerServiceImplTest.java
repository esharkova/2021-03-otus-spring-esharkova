package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.generator.ClientGenerator;
import ru.diasoft.micro.generator.LegalsGenerator;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.LegalInsertDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveLegalInsertSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ReceiveLegalInsertSubscribeListenerServiceImpl insertSubscribeListenerService;

    @Test
    void recieveLegalInsertTest() {
        Client client = ClientGenerator.getClientLegal();
        when(clientService.save(any())).thenReturn(client);

        LegalInsertDto legalInsertDto = LegalsGenerator.getLegalInsertDto();
        insertSubscribeListenerService.receiveLegalInsert(legalInsertDto);
        Client insertDtoToClient = ClientMapper.INSTANCE.LegalInsertDtoToClient(legalInsertDto);

        assertThat(insertDtoToClient).isEqualTo(client);
        verify(clientService, times(1)).save(any());
    }
}