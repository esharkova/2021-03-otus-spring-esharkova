package ru.diasoft.micro.feign.digitalmarketprofile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.domain.securityprofiledto.RestResponsePage;
import ru.diasoft.micro.domain.securityprofiledto.SecurityResponseDto;
import ru.diasoft.micro.domain.securityprofiledto.SecuritySearchParamDto;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.util.LoggerUtils1;

import java.util.ArrayList;
import java.util.List;

import static ru.diasoft.micro.constants.MainConstants.USE_TECH_TOKEN;

@Service
@RequiredArgsConstructor
/**
 * фасад для вызова API сервиса "Профиль ценной бумаги".
 * использует для работы feign
 */
@Loggable
public class SecurityProfileClientFacade {
    private final SecurityProfileClient securityProfileClient;

    public List<Security> getSecuritiesBySearchParam(SecuritySearchParamDto securitySearchParam) {
        RestResponsePage<SecurityResponseDto> response = securityProfileClient.getSecuritiesBySearchParam(USE_TECH_TOKEN, securitySearchParam);
        List<Security> securityList = new ArrayList<>();
        response.getContent().forEach(sec ->
                securityList.add(Security.builder()
                        .isin(sec.getIsinCode())
                        .securityID(sec.getSecurityId())
                        .securityName(sec.getName())
                        .securityType(getSecurityType(sec))
                        .securityCode(sec.getNsdCode())
                        .securityBrief("")
                        .securityCode("")
                        .build())
        );
        return securityList;
    }

    private Integer getSecurityType(SecurityResponseDto security) {
        try {
            return Integer.parseInt(security.getSecurityType());
        } catch (Exception e) {
            return 0;
        }
    }
}
