package ru.diasoft.micro.service.cache;

import ru.diasoft.micro.domain.Security;

public interface SecurityService {

    Security findBySecurityCode(String securityCode);

    Security findByIsin(String isin);

    Security findBySecurityId(Long securityId);

    void delete(Long securityId);

    Security save(Security security);
}
