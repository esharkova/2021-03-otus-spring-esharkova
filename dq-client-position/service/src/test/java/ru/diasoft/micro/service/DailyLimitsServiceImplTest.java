package ru.diasoft.micro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import ru.diasoft.micro.domain.Limit;
import ru.diasoft.micro.generator.DailyLimitGenerator;
import ru.diasoft.micro.generator.LimitGenerator;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.repository.LimitRepository;
import ru.diasoft.micro.repository.PositionRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DailyLimitsServiceImplTest {

    @Mock
    private LimitRepository limitRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private LimitSupportService limitSupportService;

    @InjectMocks
    DailyLimitsServiceImpl dailyLimitsService;

    Limit limit;

    @BeforeEach
    void setUp() {
        limit = LimitGenerator.getOkLimit();

    }

    @Test
    void transformNullSecAndMoneyTest() throws ParseException {
        DailyLimitsDto dailyLimitsDto = DailyLimitGenerator.getLimit();
        List<Limit> limitList = new ArrayList<>();
        limitList.add(limit);
        when(limitRepository.findAll()).thenReturn(limitList);
        when(limitSupportService.limitBuilder(any())).thenReturn(limit);
        dailyLimitsService.transform(dailyLimitsDto);
        verify(limitSupportService, times(1)).limitBuilder(dailyLimitsDto);
        verify(limitRepository, times(2)).save(limit);
    }

    @Test
    void transformNotNullSecAndMoneyTest() throws ParseException {
        DailyLimitsDto dailyLimitsDto = DailyLimitGenerator.getLimit();

        List<Limit> limitList = new ArrayList<>();
        limitList.add(limit);

        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        PositionProjection positionProjection = projectionFactory.createProjection(PositionProjection.class);
        positionProjection.setPositionID(1L);
        positionProjection.setPositionDateKind(1);
        positionProjection.setOutRest(new BigDecimal("2.1"));
        positionProjection.setAssetName("assetName");

        List<PositionProjection> positionProjections = new ArrayList<>();
        positionProjections.add(positionProjection);

        when(limitRepository.findAll()).thenReturn(limitList);
        when(limitSupportService.limitBuilder(any())).thenReturn(limit);
        when(positionRepository.getPositionLimit(any(), any())).thenReturn(positionProjections);

        dailyLimitsService.transform(dailyLimitsDto);
        
        verify(limitSupportService, times(1)).limitBuilder(dailyLimitsDto);
        verify(limitRepository, times(2)).save(limit);
        verify(positionRepository, times(2)).getPositionLimit(any(),any());
    }
}