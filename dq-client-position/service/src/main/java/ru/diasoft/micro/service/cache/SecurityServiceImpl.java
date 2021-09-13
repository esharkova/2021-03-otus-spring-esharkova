package ru.diasoft.micro.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.repository.SecurityRepository;
import ru.diasoft.micro.service.cache.SecurityService;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final SecurityRepository securityRepository;

    @Override
    @Cacheable(cacheNames="security",key="{#securityCode}")
    public Security findBySecurityCode(String securityCode) {
        return securityRepository.findBySecurityCode(securityCode).orElse(null);
    }

    @Override
    @Cacheable(cacheNames="security",key="{#isin}")
    public Security findByIsin(String isin) {
        return securityRepository.findByIsin(isin).orElse(null);
    }

    @Override
    @Cacheable(cacheNames="security",key="{#securityId}")
    public Security findBySecurityId(Long securityId) {
        return securityRepository.findById(securityId).orElse(null);
    }

    @Override
    @CacheEvict("security")
    public void delete(Long securityId) {
        securityRepository.deleteById(securityId);
    }

    @Override
    @CachePut("security")
    public Security save(Security security) {
        return securityRepository.save(security);
    }
}
