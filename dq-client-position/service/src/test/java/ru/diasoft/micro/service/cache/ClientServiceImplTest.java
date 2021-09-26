package ru.diasoft.micro.service.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.generator.ClientGenerator;
import ru.diasoft.micro.repository.ClientRepository;
import ru.diasoft.micro.service.cache.ClientServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ClientServiceImplTest {

    public static final String EXTERNAL_NUMBER = "externalNumber";

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    Client client;

    @BeforeEach
    void setUp() {
        client = ClientGenerator.getClient();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByExternalNumber() {
        when(clientRepository.findByExternalNumber(EXTERNAL_NUMBER))
                .thenReturn(java.util.Optional.ofNullable(client));
        Client findClient = clientService.findByExternalNumber(EXTERNAL_NUMBER);
        assertNotNull(findClient);
        assertEquals(findClient,client);
        verify(clientRepository, times(1)).findByExternalNumber(EXTERNAL_NUMBER);
    }

    @Test
    void saveClientTest() {
        when(clientRepository.save(any())).thenReturn(client);
        Client createdClient = clientService.save(client);
        assertEquals(createdClient, client);
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void deleteClientTest() {
        when(clientRepository.save(any())).thenReturn(client);
        Client createdClient = clientService.save(client);
        assertNotNull(createdClient);
        clientService.delete(createdClient.getClientID());
        Client foundClient = clientService.findByExternalNumber(createdClient.getExternalNumber());
        assertNull(foundClient);
    }
}