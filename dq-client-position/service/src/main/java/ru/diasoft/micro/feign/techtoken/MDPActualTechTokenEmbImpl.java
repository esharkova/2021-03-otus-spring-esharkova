package ru.diasoft.micro.feign.techtoken;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.diasoft.micro.mdp.mdptechtoken.Cache;
import ru.diasoft.micro.mdp.mdptechtoken.MDPActualTechToken;
import ru.diasoft.micro.mdp.mdptechtoken.clients.AuthServerFeignClient;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@Profile("dev")
@Primary
@ConditionalOnProperty(
        prefix = "mdp.tokenService",
        name = {"enabled"},
        havingValue = "true"
)
// Класс нужен только при отладке и является копией от библиотеки mdptechtoken
@SuppressWarnings({"squid:S3776", "squid:S3740", "squid:S3740", "squid:S1192",
        "squid:S3457", "squid:S2629", "squid:S1166"})
public class MDPActualTechTokenEmbImpl implements MDPActualTechToken {
    private final Logger log = LoggerFactory.getLogger(MDPActualTechTokenEmbImpl.class);
    private final AuthServerFeignClient authServerFeignClient;
    private final MDPAuthFeignClient mdpAuthFeignClient;
    private final RestTemplate restTemplate;
    @Value("${mdp.tokenService.username}")
    private String username;
    @Value("${mdp.tokenService.password}")
    private String password;
    @Value("${mdp.tokenService.service}")
    private String service;
    private Supplier<String> token = null;

    @PostConstruct
    void init() {
        this.token = Cache.cache((ont) -> {
            Map tokenMap = null;
            String var3 = this.service;
            byte var4 = -1;
            switch (var3.hashCode()) {
                case -1397936405:
                    if (var3.equals("authserver")) {
                        var4 = 0;
                    }
                    break;
                case 923008737:
                    if (var3.equals("mdpauth")) {
                        var4 = 1;
                    }
                    break;
                default:
                    break;
            }

            switch (var4) {
                case 0:
                    tokenMap = this.authServerFeignClient.getToken("Basic Y2xpZW50OnNlY3JldA==", "uaa", "oauth/token", this.username, this.password, "password", "openid");
                    break;
                case 1:
                    Map<String, Object> params = new HashMap();
                    params.put("username", this.username);
                    params.put("password", this.password);
                    tokenMap = this.mdpAuthFeignClient.getToken(params);
                    break;
                default:
                    if (this.service.startsWith("http://")) {
                        try {
                            HttpEntity<String> request = this.createRequest(this.username, this.password);
                            ResponseEntity<String> response = this.restTemplate.postForEntity(this.service, request, String.class, new Object[0]);
                            if (response != null && HttpStatus.OK.equals(response.getStatusCode())) {
                                ObjectMapper mapper = new ObjectMapper();
                                TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
                                };
                                tokenMap = (Map) mapper.readValue((String) response.getBody(), typeRef);
                            }
                        } catch (Exception var10) {
                            this.log.error(var10.getMessage(), var10);
                        }
                    }
            }

            if (tokenMap != null) {
                Map finalTokenMap = tokenMap;
                this.log.debug("Token: " + tokenMap.keySet().stream().map((key) -> {
                    return key + "=" + finalTokenMap.get(key);
                }).collect(Collectors.joining(", ", "{", "}")).toString());
                String access_token = String.valueOf(tokenMap.get("access_token"));
                Long expires_in = Long.parseLong(String.valueOf(tokenMap.get("expires_in"))) * 1000L;
                ont.setTimeLive((long) ((double) expires_in * 0.9D));
                return access_token;
            } else {
                this.log.error("Failed getting actual token");
                return null;
            }
        }, 0L);
    }

    private HttpEntity<String> createRequest(String username, String password) throws JsonProcessingException {
        Map<String, Object> params = new HashMap();
        params.put("username", username);
        params.put("password", password);
        String body = (new ObjectMapper()).writeValueAsString(params);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(body, headers);
        return request;
    }

    public String getActualTechToken() {
        String t = (String) this.token.get();
        this.log.debug("Actual access token: " + t);
        return t;
    }

    public MDPActualTechTokenEmbImpl(AuthServerFeignClient authServerFeignClient, MDPAuthFeignClient mdpAuthFeignClient, RestTemplate restTemplate) {
        this.authServerFeignClient = authServerFeignClient;
        this.mdpAuthFeignClient = mdpAuthFeignClient;
        this.restTemplate = restTemplate;
    }
}
