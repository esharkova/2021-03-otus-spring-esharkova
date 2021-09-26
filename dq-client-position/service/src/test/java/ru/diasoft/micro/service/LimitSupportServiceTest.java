package ru.diasoft.micro.service;

import org.hibernate.type.ZonedDateTimeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.micro.domain.Limit;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.enums.FixFlag;
import ru.diasoft.micro.enums.PositionDateKind;
import ru.diasoft.micro.event.subscriber.ReceiveDailyLimitsSubscribeListenerServiceImpl;
import ru.diasoft.micro.generator.DailyLimitGenerator;
import ru.diasoft.micro.generator.LimitGenerator;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.repository.LimitRepository;
import ru.diasoft.micro.repository.PositionRepository;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LimitSupportServiceTest {
    @Mock
    PositionRepository positionRepository;
    @Mock
    LimitRepository limitRepository;

    @InjectMocks
    private LimitSupportService limitSupportService;

    Limit limit;

    @BeforeEach
    void setUp() {

        limit = LimitGenerator.getLimit();
    }

    @Test
    void limitBuilderTest() throws ParseException {
        DailyLimitsDto dailyLimitsDto = DailyLimitGenerator.getLimit();
        List<Position> positionList = new ArrayList<>();
        positionList.add(Position.builder()
                        .positionDateTime(ZonedDateTime.now())
                        .build());
        when(positionRepository.findByFixFlagAndPositionDateKind(any(), any())).thenReturn(positionList);
        Limit createdLimit = limitSupportService.limitBuilder(dailyLimitsDto);
        assertEquals(limit, createdLimit);
        verify(positionRepository, times(2))
                .findByFixFlagAndPositionDateKind(FixFlag.NOT_FIXED.getValue(), PositionDateKind.T0.getValue());
    }
}