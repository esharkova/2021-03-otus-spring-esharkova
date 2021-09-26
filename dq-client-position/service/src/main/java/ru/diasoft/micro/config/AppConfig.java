package ru.diasoft.micro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.cron}")
    private String cronValue;

    @Value("${app.position.tradePortfolio}")
    private String tradePortfolio;


    @Bean
    public String cronBean() {
        return this.cronValue;
    }

    @Bean
    public String tradePortfolioBean() {
        return this.tradePortfolio;
    }
}
