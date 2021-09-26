package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.receivedailylimits.subscribe.ReceiveDailyLimitsSubscribeListenerService;
import ru.diasoft.micro.service.LimitSendingService;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceiveDailyLimitsSubscribeListenerServiceImpl implements ReceiveDailyLimitsSubscribeListenerService {
    private final LimitSendingService limitSendingService;

    @Override
    public void receiveDailyLimits(DailyLimitsDto msg)  {
            limitSendingService.parse(msg);
    }
}
