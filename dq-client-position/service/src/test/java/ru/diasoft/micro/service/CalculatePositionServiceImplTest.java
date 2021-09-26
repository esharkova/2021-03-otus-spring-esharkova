package ru.diasoft.micro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.enums.FixFlag;
import ru.diasoft.micro.repository.PositionRepository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Сервис CalculatePositionService")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CalculatePositionServiceImplTest {
    public static final String CLIENT_CODE = "clientCode";
    public static final String AGREEMENT_CODE = "agreementCode";
    public static final String PORTFOLIO_BRIEF = "portfolioBrief";
    public static final String POSITION_DATE_KIND = "positionDateKind";
    public static final String TRADING_ACC = "tradingAcc";
    public static final String DEPO_STORAGE_LOCATION = "depoStorageLocation";
    public static final String FIN_INSTRUMENT_CODE = "finInstrumentCode";
    public static final int POSITION_TYPE = 1;
    public static final BigDecimal INCOME_REST = new BigDecimal(10000);
    public static final BigDecimal EXPENSE_REST =  new BigDecimal(6000);
    public static final BigDecimal RECALC_EXPENSE_REST =  new BigDecimal(7000);;
    public static final BigDecimal INCOME = new BigDecimal(1000);
    public static final BigDecimal RECALC_INCOME = new BigDecimal(2000);
    public static final BigDecimal EXPENSE =  new BigDecimal(5000);;
    public static final int NOT_FIXED = FixFlag.NOT_FIXED.getValue();
    public static final int FIXED = FixFlag.FIXED.getValue();
    public static final Long USER_ID = new Long(0);
    public static final int MARGIN_FLAG = 1;
    public static final int POSITION_DATE_KIND_T_0_VALUE = 2;
    public static final Integer POSITION_DATE_KIND_TMINUS_1 = 1;
    public static final Long OPERATION_ID = new Long(1);

    private Position position, recalcPosition;
    private CalculatePositionServiceImpl calculatePositionService;


    @Mock
    private PositionService positionService;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private MessageSendingService messageSendingService;

    @Mock
    private TransformOperation transformOperation;


    @BeforeEach
    void setUp() {

        calculatePositionService = new CalculatePositionServiceImpl(positionService, positionRepository,messageSendingService, transformOperation);

        position = Position.builder()
                .positionDateKind(POSITION_DATE_KIND_T_0_VALUE)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(INCOME)
                .expense(EXPENSE)
                .outRest(EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(NOT_FIXED)
                .fixUserID(USER_ID)
                .build();

        recalcPosition = Position.builder()
                .positionDateKind(POSITION_DATE_KIND_T_0_VALUE)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(RECALC_INCOME)
                .expense(EXPENSE)
                .outRest(RECALC_EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(NOT_FIXED)
                .fixUserID(USER_ID)
                .build();
    }




    /*@Test
    void savePositionTest() {

        when(positionService.createPosition(any())).thenReturn(position);
        when(transformOperation.transformPositionToDto(any())).thenReturn(any());

        int sizeBeforeSave = positionService.findByFixFlagAndPositionDateKindGreaterThan(NOT_FIXED, POSITION_DATE_KIND_TMINUS_1).size();
        calculatePositionService.savePosition(position);
        int sizeAfterSave = positionService.findByFixFlagAndPositionDateKindGreaterThan(NOT_FIXED, POSITION_DATE_KIND_TMINUS_1).size();

        assertEquals(positionService.createPosition(any()), position);
        assertThat(sizeAfterSave).isGreaterThanOrEqualTo(sizeBeforeSave);

        verify(positionService, times(2)).createPosition(any());
        verify(positionService, times(2)).findByFixFlagAndPositionDateKindGreaterThan(NOT_FIXED, POSITION_DATE_KIND_TMINUS_1);

    }*/


    /*@Test
    void recalculatePositionTest() {
        when(positionService.findPositonByKeyParams(
                "CLIENT_CODE",
                "AGREEMENT_CODE",
                "PORTFOLIO_BRIEF",
                "TRADING_ACC",
                "FIN_INSTRUMENT_CODE",
                POSITION_DATE_KIND_T_0_VALUE,
                FixFlag.NOT_FIXED)).thenReturn(position);

        when(positionService.createPosition(any())).thenReturn(recalcPosition);

        assertThat(positionService.findPositonByKeyParams("CLIENT_CODE",
                "AGREEMENT_CODE",
                "PORTFOLIO_BRIEF",
                "TRADING_ACC",
                "FIN_INSTRUMENT_CODE",
                POSITION_DATE_KIND_T_0_VALUE,
                FixFlag.NOT_FIXED).getIncome()).isNotEqualByComparingTo(positionService.createPosition(any()).getIncome());

        assertThat(positionService.findPositonByKeyParams("CLIENT_CODE",
                "AGREEMENT_CODE",
                "PORTFOLIO_BRIEF",
                "TRADING_ACC",
                "FIN_INSTRUMENT_CODE",
                POSITION_DATE_KIND_T_0_VALUE,
                FixFlag.NOT_FIXED).getOutRest()).isNotEqualByComparingTo(positionService.createPosition(any()).getOutRest());

        verify(positionService, times(2)).createPosition(any());
        verify(positionService, times(2)).findPositonByKeyParams(
                "CLIENT_CODE",
                "AGREEMENT_CODE",
                "PORTFOLIO_BRIEF",
                "TRADING_ACC",
                "FIN_INSTRUMENT_CODE",
                POSITION_DATE_KIND_T_0_VALUE,
                FixFlag.NOT_FIXED);
    }*/

    @Test
    void recalculateNextPositionTest() {
        when(positionService.createPosition(any())).thenReturn(position);

        int sizeBeforeSave = positionService.findByFixFlagAndPositionDateKindGreaterThan(NOT_FIXED, POSITION_DATE_KIND_T_0_VALUE).size();
        calculatePositionService.recalculateNextPosition(position);
        int sizeAfterSave = positionService.findByFixFlagAndPositionDateKindGreaterThan(NOT_FIXED, POSITION_DATE_KIND_T_0_VALUE).size();

        assertEquals(positionService.createPosition(any()), position);
        assertThat(sizeBeforeSave).isBetween(0,4);
        assertThat(sizeAfterSave).isGreaterThanOrEqualTo(sizeBeforeSave);

        verify(positionService, times(1)).createPosition(any());
    }
}