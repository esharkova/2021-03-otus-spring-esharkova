package ru.diasoft.micro.feign.digitalmarketprofile;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.diasoft.micro.domain.securityprofiledto.RestResponsePage;
import ru.diasoft.micro.domain.securityprofiledto.SecurityResponseDto;
import ru.diasoft.micro.domain.securityprofiledto.SecuritySearchParamDto;

import javax.validation.Valid;

import static ru.diasoft.micro.constants.MainConstants.HEADER_USE_TECH_TOKEN;

/**
 * @author mkushcheva
 * Интерфейс для вызова API сервиса "Профиль ценной бумаги" (МКС - цифровой профиль рынка )через feign.
 */

@Validated
@FeignClient(name = "qdmpsecurity", url = "${dmp.securityProfileUrl}")
public interface SecurityProfileClient {
    /**
     * Получение информации по ценным бумагам по параметрам
     *
     * @param securitySearchParam Параметры поиска ценных бумаг
     */
    @PostMapping("${dmp.securityProfileGet}")
    RestResponsePage<@Valid SecurityResponseDto> getSecuritiesBySearchParam(@RequestHeader(HEADER_USE_TECH_TOKEN) String useTechToken, @RequestBody SecuritySearchParamDto securitySearchParam);
}