package ru.diasoft.micro.event.subscriber;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.generator.DailyLimitGenerator;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.service.LimitSendingService;
import ru.diasoft.micro.service.LimitsTransformService;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReceiveDailyLimitsSubscribeListenerServiceImplTest {

    @Mock
    private LimitSendingService limitSendingService;

    @InjectMocks
    private ReceiveDailyLimitsSubscribeListenerServiceImpl receiveDailyLimitsSubscribeListenerService;

    @Test
    @Disabled
    void recieveInstitutionDeleteTest() throws ParseException {
        DailyLimitsDto dailyLimitsDto = DailyLimitGenerator.getLimit();
        receiveDailyLimitsSubscribeListenerService.receiveDailyLimits(dailyLimitsDto);
        verify(limitSendingService, times(1)).parse(dailyLimitsDto);
    }

}