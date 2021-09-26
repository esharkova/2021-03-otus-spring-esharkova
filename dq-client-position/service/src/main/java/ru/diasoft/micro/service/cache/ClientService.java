package ru.diasoft.micro.service.cache;

import ru.diasoft.micro.domain.Client;

public interface ClientService {
    Client findByExternalNumber(String externalNumber);

    void delete(Long clientId);

    Client save(Client client);

}
