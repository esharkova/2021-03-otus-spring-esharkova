package ru.diasoft.micro.feign.techtoken;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("dev")
@Primary
@FeignClient(name = "mdpauth2", url = "http://mdpauth.dqclientprtf-dqclientprtf.qrun.diasoft.ru")
//Класс используется только при отладке локально, копия класса из библиотеки mdptechtoken
@SuppressWarnings({"squid:S3740"})
public interface MDPAuthFeignClient {
    @Headers({"Content-Type: application/json"})
    @PostMapping(
            value = {"/mdpauth/oauth/token"}
    )
    Map getToken(@RequestBody Map<String, Object> var1);

    @GetMapping(
            path = {"/mdpauth/authschemas/v1/{id}"}
    )
    Map<String, Object> getAuthSchemaById(@RequestHeader Map<String, String> var1, @PathVariable("id") Integer var2);
}

