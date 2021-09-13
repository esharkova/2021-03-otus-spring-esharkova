package ru.diasoft.micro.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Client;
import ru.diasoft.micro.repository.ClientRepository;
import ru.diasoft.micro.service.cache.ClientService;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    @Cacheable(cacheNames="client",key="{#externalNumber}")
    public Client findByExternalNumber(String externalNumber) {
        return clientRepository.findByExternalNumber(externalNumber).orElse(null);
    }

    @Override
    @CacheEvict("client")
    public void delete(Long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Override
    @CachePut("client")
    public Client save(Client client) {
        return clientRepository.save(client);
    }
}
