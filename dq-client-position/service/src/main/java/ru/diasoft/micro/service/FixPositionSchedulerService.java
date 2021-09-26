package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@EnableScheduling
public class FixPositionSchedulerService {


    private final FixPositionServiceImpl fixPositionService;

    @Scheduled(cron = "#{@cronBean}")
    public void createFixPosition(){
        fixPositionService.fixPosition();
    }


}
