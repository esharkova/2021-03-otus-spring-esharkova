package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.generator.ClientGenerator;
import ru.diasoft.micro.generator.InstitutionsGenerator;
import ru.diasoft.micro.mapper.ClientMapper;
import ru.diasoft.micro.model.InstitutionUpdateDto;
import ru.diasoft.micro.service.cache.AgreementServiceImpl;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveInstitutionUpdateSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @Mock
    private AgreementServiceImpl agreementService;

    @InjectMocks
    private ReceiveInstitutionUpdateSubscribeListenerServiceImpl updateSubscribeListenerService;

    @Test
    void recieveInstitutionUpdateTest() {
        Client client = ClientGenerator.getClientInstitution();
        when(clientService.save(any())).thenReturn(client);

        InstitutionUpdateDto institutionUpdateDto = InstitutionsGenerator.getInstitutionUpdateDto();
        updateSubscribeListenerService.receiveInstitutionUpdate(institutionUpdateDto);
        Client updateDtoToClient = ClientMapper.INSTANCE.InstitutionUpdateDtoToClient(institutionUpdateDto);

        assertThat(updateDtoToClient).isEqualTo(client);
        verify(clientService, times(1)).save(any());
        verify(agreementService, times(1)).updateAgreementClient(any());
    }
}