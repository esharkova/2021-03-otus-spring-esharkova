package ru.diasoft.micro.service;

import org.junit.jupiter.api.BeforeEach;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PositionServiceImplTest {

    public static final String TRADING_ACC = "tradingAcc";
    public static final String FIN_INSTRUMENT_CODE = "finInstrumentCode";
    public static final int POSITION_TYPE = 1;
    public static final BigDecimal INCOME_REST = new BigDecimal(10000);
    public static final BigDecimal EXPENSE_REST =  new BigDecimal(6000);;
    public static final BigDecimal INCOME = new BigDecimal(1000);
    public static final BigDecimal EXPENSE =  new BigDecimal(5000);;
    public static final int FIX_FLAG = 0;
    public static final Long USER_ID = new Long(0);
    public static final int MARGIN_FLAG = 1;
    public static final int POSITION_DATE_KIND_T_0_VALUE = 2;


    @Mock
    private PositionRepository positionRepository;
    private List<Position> positionList;
    private PositionService positionService;
    private Position position;
    private Position createdPosition;

    @BeforeEach
    void setUp() {
        positionService = new PositionServiceImpl(positionRepository);

        position = Position.builder()
                .positionDateKind(POSITION_DATE_KIND_T_0_VALUE)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(INCOME)
                .expense(EXPENSE)
                .outRest(EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(FIX_FLAG)
                .fixUserID(USER_ID)
                .build();

        positionList = new ArrayList<>();
        positionList.add(position);
    }


    @Test
    void createPositionTest() {
        when(positionRepository.save(any())).thenReturn(position);
        createdPosition =  positionService.createPosition(position);
        assertEquals(createdPosition, position);
        verify(positionRepository, times(1)).save(any());
    }

  /*  @Test
    void deletePositionTest() {
        when(positionRepository.save(any())).thenReturn(position);
        Position createdPosition = positionService.createPosition(position);
        assertNotNull(createdPosition);
        positionService.deletePosition(createdPosition);
        Position foundPosition = positionService.findPositonByKeyParams(createdPosition.getClientCode(),
                createdPosition.getAgreementCode(), createdPosition.getPortfolioBrief(), createdPosition.getTradingAcc(),
                createdPosition.getAsset(), createdPosition.getPositionDateKind(), FixFlag.NOT_FIXED);
        assertNull(foundPosition);
        verify(positionRepository, times(1)).save(any());
    }*/

    @Test
    void savePositionListTest() {
        when(positionRepository.saveAll(any())).thenReturn(positionList);
        List<Position> createdPositionList = positionService.savePositionList(positionList);
        assertEquals(1, createdPositionList.size());
        assertEquals(positionList, createdPositionList);
        verify(positionRepository, times(1)).saveAll(any());
    }


    @Test
    void getNextPositionDateKindTestAndGetPreviousPositionDateKindTest() {
        when(positionRepository.save(any())).thenReturn(position);
        Position createdPosition = positionService.createPosition(position);
        assertEquals(position.getPositionDateKind() + 1,
                positionService.getNextPositionDateKind(createdPosition.getPositionDateKind()));
        assertEquals(position.getPositionDateKind() - 1,
                positionService.getPreviousPositionDateKind(createdPosition.getPositionDateKind()));
    }
}