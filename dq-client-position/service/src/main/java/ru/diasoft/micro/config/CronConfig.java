package ru.diasoft.micro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CronConfig {

    @Value("${app.cron}")
    private String cronValue;

    @Bean
    public String cronBean() {
        return this.cronValue;
    }
}
