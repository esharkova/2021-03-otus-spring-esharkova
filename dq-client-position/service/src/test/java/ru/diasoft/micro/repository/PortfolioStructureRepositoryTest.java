package ru.diasoft.micro.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.PortfolioStructure;
import ru.diasoft.micro.generator.PortfolioStructureGenerator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Репозиторий по работе с портфелями должен:")
class PortfolioStructureRepositoryTest {

    public static final String TRADEPORTFOLIO = "tradePortfolio";
    public static final String CLIENT_CODE = "clientCode";
    public static final int TRADE_PLACE = 1;
    public static final String DEPO_SUB_ACCOUNT = "depoSubAccount";
    public static final Long AGREEMENT_ID = 1L;

    @Autowired
    private PortfolioStructureRepository repository;

    PortfolioStructure portfolioStructure, createdPortfolioStructure;

    @BeforeEach
    void setUp() {
        portfolioStructure = PortfolioStructureGenerator.getPortfolioStructure();

        createdPortfolioStructure = repository.save(portfolioStructure);
    }

    @AfterEach
    void tearDown() {
        repository.delete(createdPortfolioStructure);
    }

    @Test
    @DisplayName("Сохранять портфель")
    void portfolioStructureCreateTest() {

        assertThat(portfolioStructure).isEqualToIgnoringGivenFields(createdPortfolioStructure,"portfolioStructureID");
        assertThat(createdPortfolioStructure.getPortfolioStructureID()).isNotNull();

    }

    @Test
    @DisplayName("Искать портфель по наименеованию")
    void findByTradePortfolioTest() {
        Optional<PortfolioStructure> findPortfolioStructure = repository.findByBrokerAgreementIDAndTradePortfolio(AGREEMENT_ID, TRADEPORTFOLIO);
        assertThat(findPortfolioStructure).isNotNull();
    }

    @Test
    @DisplayName("Искать портфель по коду клиента")
    void findByClientCodeTest() {
        Optional<PortfolioStructure> findPortfolioStructure = repository.findByBrokerAgreementIDAndClientCode(AGREEMENT_ID, CLIENT_CODE);
        assertThat(findPortfolioStructure).isNotNull();

    }

    @Test
    @DisplayName("Искать портфель по месту совершения и разделу счета депо")
    void findByTradePlaceAndDepoSubAccountTest() {
        Optional<PortfolioStructure> findPortfolioStructure = repository.findByBrokerAgreementIDAndTradePlaceAndDepoSubAccount(AGREEMENT_ID, TRADE_PLACE,DEPO_SUB_ACCOUNT);
        assertThat(findPortfolioStructure).isNotNull();

    }

    @Test
    @DisplayName("Искать портфель по месту разделу счета депо")
    void findByDepoSubAccountTest() {
        Optional<PortfolioStructure> findPortfolioStructure = repository.findByBrokerAgreementIDAndDepoSubAccount(AGREEMENT_ID, DEPO_SUB_ACCOUNT);
        assertThat(findPortfolioStructure).isNotNull();

    }
}