package ru.diasoft.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@EnableCaching
@EnableJpaRepositories({"ru.diasoft.micro"})
@EntityScan({"ru.diasoft.micro"})
@SpringBootApplication
@EnableFeignClients
public class DqclntposApplication {

    public static void main(String[] args) { SpringApplication.run(DqclntposApplication.class, args);
    }

}
