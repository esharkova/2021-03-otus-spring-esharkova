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
import ru.diasoft.micro.model.LegalUpdateDto;
import ru.diasoft.micro.service.cache.AgreementServiceImpl;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveLegalUpdateSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private AgreementServiceImpl agreementService;

    @InjectMocks
    private ReceiveLegalUpdateSubscribeListenerServiceImpl updateSubscribeListenerService;

    @Test
    void recieveLegalUpdateTest() {
        Client client = ClientGenerator.getClientLegal();
        when(clientService.save(any())).thenReturn(client);

        LegalUpdateDto legalUpdateDto = LegalsGenerator.getLegalUpdateDto();
        updateSubscribeListenerService.receiveLegalUpdate(legalUpdateDto);
        Client updateDtoToClient = ClientMapper.INSTANCE.LegalUpdateDtoToClient(legalUpdateDto);

        assertThat(updateDtoToClient).isEqualTo(client);
        verify(clientService, times(1)).save(client);
        verify(agreementService, times(1)).updateAgreementClient(any());
    }
}