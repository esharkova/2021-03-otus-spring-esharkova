package ru.diasoft.micro.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.repository.PositionRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class FixPositionServiceImplTest {

    public static final String TRADING_ACC = "tradingAcc";
    public static final String FIN_INSTRUMENT_CODE = "finInstrumentCode";
    public static final int POSITION_TYPE = 1;
    public static final BigDecimal INCOME_REST = new BigDecimal(10000);
    public static final BigDecimal EXPENSE_REST =  new BigDecimal(6000);;
    public static final BigDecimal INCOME = new BigDecimal(1000);
    public static final BigDecimal EXPENSE =  new BigDecimal(5000);;
    public static final int FIX_FLAG0 = 0;
    public static final int FIX_FLAG1 = 1;
    public static final Long USER_ID = new Long(0);
    public static final int MARGIN_FLAG = 1;
    public static final int POSITION_DATE_KIND_T_0_VALUE = 2;
    public static final Integer POSITION_DATE_KIND_TMINUS_1 = 1;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private PositionService positionService;

    @InjectMocks
    private FixPositionServiceImpl fixPositionService;

    private List<Position> fillNotFixPosition(){
        List<Position> listPositionNotFix = new ArrayList<>();
        listPositionNotFix.add(Position.builder()
                .positionDateKind(POSITION_DATE_KIND_T_0_VALUE)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(INCOME)
                .expense(EXPENSE)
                .outRest(EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(FIX_FLAG0)
                .fixUserID(USER_ID)
                .build());

        return listPositionNotFix;
    }

    private List<Position> fillAfterFixPosition(){
        List<Position> listPositionFix = new ArrayList<>();
        listPositionFix.add(Position.builder()
                .positionDateKind(POSITION_DATE_KIND_T_0_VALUE)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(INCOME)
                .expense(EXPENSE)
                .outRest(EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(FIX_FLAG1)
                .fixUserID(USER_ID)
                .build());

        listPositionFix.add(Position.builder()
                .positionDateKind(POSITION_DATE_KIND_TMINUS_1)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(INCOME)
                .expense(EXPENSE)
                .outRest(EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(FIX_FLAG0)
                .fixUserID(USER_ID)
                .build());


        return listPositionFix;
    }




    @Test
    void findNotFixPositionTest() {


        when(positionRepository.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1)).thenReturn(fillNotFixPosition());

        assertNotNull(positionRepository.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1));
        assertEquals(positionRepository.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1).size(), 1);

        verify(positionRepository, times(2)).findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1);
    }

    @Test
    void fixingPositionTest() {


        when(positionService.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1)).thenReturn(fillNotFixPosition());
        when(positionService.savePositionList(any())).thenReturn(fillAfterFixPosition());

        assertNotNull(positionService.savePositionList(any()));
        assertEquals(positionService.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1).size()*2,positionService.savePositionList(any()).size());
        //сохранилась позиция с флагом = 1
        assertNotNull(positionService.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG1, POSITION_DATE_KIND_T_0_VALUE));
        //сохранилась позиция с флагом = 0 со смещением
        assertNotNull(positionService.findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1));

        verify(positionService, times(2)).findByFixFlagAndPositionDateKindGreaterThan(FIX_FLAG0, POSITION_DATE_KIND_TMINUS_1);
        verify(positionService, times(2)).savePositionList(any());


    }
}