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
import ru.diasoft.micro.model.InstitutionInsertDto;
import ru.diasoft.micro.service.cache.ClientServiceImpl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiveInstitutionInsertSubscribeListenerServiceImplTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ReceiveInstitutionInsertSubscribeListenerServiceImpl insertSubscribeListenerService;

    @Test
    void recieveInstitutionInsertTest() {
        Client client = ClientGenerator.getClientInstitution();
        when(clientService.save(any())).thenReturn(client);

        InstitutionInsertDto institutionInsertDto = InstitutionsGenerator.getInstitutionInsertDto();
        insertSubscribeListenerService.receiveInstitutionInsert(institutionInsertDto);
        Client insertDtoToClient = ClientMapper.INSTANCE.InstitutionInsertDtoToClient(institutionInsertDto);

        assertThat(insertDtoToClient).isEqualTo(client);
        verify(clientService, times(1)).save(any());

    }
}