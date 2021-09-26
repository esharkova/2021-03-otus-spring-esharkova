package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.fixingday.subscribe.FixingDaySubscribeListenerService;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.FixingDayDto;
import ru.diasoft.micro.service.FixPositionService;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class FixingDaySubscribeListenerServiceImpl implements FixingDaySubscribeListenerService {

    private final FixPositionService fixPositionService;

    @Override
    public void fixingDay(FixingDayDto msg) {

        if (msg.getIsFixed()){
            fixPositionService.fixPosition();
        }
    }
}
