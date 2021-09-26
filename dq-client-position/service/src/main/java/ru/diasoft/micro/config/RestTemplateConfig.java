package ru.diasoft.micro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import java.time.Duration;

@Configuration
@ConditionalOnProperty(name = "mdp.tokenService.enabled", havingValue = "false")
public class RestTemplateConfig {

    @Value("${rest-template.config.connectTimeout:300000}")
    private int connectTimeout;
    @Value("${rest-template.config.readTimeout:300000}")
    private int readTimeout;

    @Bean
    @Primary
    public RestTemplate restTemplate2(RestTemplateBuilder restTemplateBuilder)
    {
        return restTemplateBuilder
           .setConnectTimeout(Duration.ofMillis(connectTimeout))
           .setReadTimeout(Duration.ofMillis(readTimeout))
           .build();
    }
}