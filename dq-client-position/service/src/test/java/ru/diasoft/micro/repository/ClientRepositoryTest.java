package ru.diasoft.micro.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.generator.ClientGenerator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ClientRepositoryTest {
    public static final String EXTERNAL_NUMBER = "externalNumber";

    @Autowired
    private ClientRepository clientRepository;

    Client client, createdClient;

    @BeforeEach
    void setUp() {

        client = ClientGenerator.getClient();

        createdClient = clientRepository.save(client);
    }

    @AfterEach
    void tearDown() {
        clientRepository.delete(createdClient);
    }

    @Test
    void clientCreateTest() {

        assertThat(createdClient).isEqualTo(client);

    }

    @Test
    void findClientByExternalNumberTest() {
        Optional<Client> findClient = clientRepository.findByExternalNumber(EXTERNAL_NUMBER);
        assertNotNull(findClient);
    }
}