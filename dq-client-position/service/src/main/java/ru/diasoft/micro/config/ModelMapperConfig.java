package ru.diasoft.micro.config;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
