package ru.diasoft.micro.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Position;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PositionRepositoryTest {

    public static final String CLIENT_CODE = "clientCode";
    public static final String AGREEMENT_CODE = "agreementCode";
    public static final String PORTFOLIO_BRIEF = "portfolioBrief";
    public static final String POSITION_DATE_KIND = "positionDateKind";
    public static final String TRADING_ACC = "tradingAcc";
    public static final String DEPO_STORAGE_LOCATION = "depoStorageLocation";
    public static final String FIN_INSTRUMENT_CODE = "finInstrumentCode";
    public static final int POSITION_TYPE = 1;
    public static final BigDecimal INCOME_REST = new BigDecimal(10000);
    public static final BigDecimal EXPENSE_REST =  new BigDecimal(6000);;
    public static final BigDecimal INCOME = new BigDecimal(1000);
    public static final BigDecimal EXPENSE =  new BigDecimal(5000);;
    public static final int FIX_FLAG0 = 0;
    public static final Long USER_ID = new Long(0);
    public static final int MARGIN_FLAG = 1;
    public static final int POSITION_DATE_KIND_T_0_VALUE = 2;


    @Autowired
    private PositionRepository positionRepository;
    private Position position;
    private Position createdPosition;

    @BeforeEach
    void setUp() {
        position = Position.builder()
                .positionDateKind(POSITION_DATE_KIND_T_0_VALUE)
                .positionType(POSITION_TYPE)
                .incomeRest(INCOME_REST)
                .income(INCOME)
                .expense(EXPENSE)
                .outRest(EXPENSE_REST)
                .fixPositionDate(ZonedDateTime.now())
                .fixFlag(FIX_FLAG0)
                .fixUserID(USER_ID)
                .build();

        createdPosition = positionRepository.save(position);
    }

    @AfterEach
    void tearDown() {
        positionRepository.delete(createdPosition);
    }


    @Test
    void operationCreateTest() {

        assertThat(position).isEqualToComparingOnlyGivenFields(createdPosition, "positionID");
        assertThat(createdPosition.getPositionID()).isNotNull();
    }

    /*@Test
    void findByClientCodeAndAgreementCodeAndPortfolioBriefAndTradingAccAndFinInstrumentCodeAndPositionDateKind() {
        Position findedPosition = positionRepository.findByClientCodeAndAgreementCodeAndPortfolioBriefAndTradingAccAndAssetAndPositionDateKindAndFixFlag(
                CLIENT_CODE,
                AGREEMENT_CODE,
                PORTFOLIO_BRIEF,
                TRADING_ACC,
                FIN_INSTRUMENT_CODE,
                POSITION_DATE_KIND_T_0_VALUE,
                FIX_FLAG0);

        assertThat(findedPosition).isNotNull();
        assertThat(findedPosition.getClientCode()).isEqualTo(CLIENT_CODE);
        assertThat(findedPosition.getAgreementCode()).isEqualTo(AGREEMENT_CODE);
        assertThat(findedPosition.getPortfolioBrief()).isEqualTo(PORTFOLIO_BRIEF);
        assertThat(findedPosition.getTradingAcc()).isEqualTo(TRADING_ACC);
        assertThat(findedPosition.getPositionDateKind()).isEqualTo(POSITION_DATE_KIND_T_0_VALUE);
    }*/

    @Test
    void positionDeleteTest(){
        int sizeBeforeDelete = positionRepository.findAll().size();
        positionRepository.delete(position);
        assertEquals(sizeBeforeDelete-1, positionRepository.findAll().size());
    }
}