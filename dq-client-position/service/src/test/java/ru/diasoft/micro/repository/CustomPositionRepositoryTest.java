package ru.diasoft.micro.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.DQCLNTPOSCustomPositions;
import ru.diasoft.micro.generator.*;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Класс репозитория для работы с таблицей пользовательская позиция должен: ")
public class CustomPositionRepositoryTest {
    @Autowired
    private CustomPositionRepository repository;
    @Autowired
    private PositionRepository posRepository;

    @Autowired
    private AgreementRepository agreementRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SecurityRepository securityRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private PortfolioStructureRepository portfolioStructureRepository;
    @Autowired
    private BrokAccountRepository brokAccountRepository;

    @AfterEach
    void repositoryDelete() {
        repository.deleteAll();
        posRepository.deleteAll();
        agreementRepository.deleteAll();
        clientRepository.deleteAll();
        securityRepository.deleteAll();
        currencyRepository.deleteAll();
        portfolioStructureRepository.deleteAll();
        brokAccountRepository.deleteAll();
    }

    @Test
    @DisplayName("удалить позицию по пользователю и типу даты")
    void deleteByCustomIDAndPositionDateKindTest() {
        List<DQCLNTPOSCustomPositions> customPositionAll = repository.saveAll(CustomPositionGenerator.getDefaultCustomPosition());
        assertThat(customPositionAll.size()).isEqualTo(3);

        repository.deleteByCustomIDAndPositionDateKind(CustomPositionGenerator.CUSTOMID1, CustomPositionGenerator.POSDATEKINDT0_VALUE);
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("заполнить таблицу КЭШ 2 записями")
    void insertCustomPositionIntoPositionTest() {
        posRepository.saveAll(CustomPositionGenerator.getPositionListForCustomPositionTest());
        repository.insertPositionIntoCustomPosition(CustomPositionGenerator.POSDATEKINDT0_VALUE, CustomPositionGenerator.CUSTOMID1, ZonedDateTime.now());
        assertThat(repository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("обогатить таблицу КЭШ")
    void enrichmentCheckTest() {
        agreementRepository.save(AgreementGenerator.getAgreement());
        clientRepository.save(ClientGenerator.getClient());
        securityRepository.save(SecurityGenerator.getSecurity());
        currencyRepository.save(CurrencyGenerator.getCurrency());
        portfolioStructureRepository.saveAll(PortfolioStructureGenerator.getPortfolioStructures());
        brokAccountRepository.save(BrokAccountGenerator.getBrokAccount());

        posRepository.saveAll(CustomPositionGenerator.getPositionListForCustomPositionTest());
        repository.insertPositionIntoCustomPosition(CustomPositionGenerator.POSDATEKINDT0_VALUE, CustomPositionGenerator.CUSTOMID1, ZonedDateTime.now());
        List<DQCLNTPOSCustomPositions> customPositions = repository.findAll();
        for (DQCLNTPOSCustomPositions customPosition : customPositions) {
            assertThat(customPosition.getAgreementNumber()).isNotNull();
            assertThat(customPosition.getClientAttr()).isNotNull();
            assertThat(customPosition.getMoneyOvernightKind()).isNotNull();
            assertThat(customPosition.getSecurityOvernightKind()).isNotNull();
            assertThat(customPosition.getMarginLendingKind()).isNotNull();
            assertThat(customPosition.getCalculationDateTime()).isNotNull();
            assertThat(customPosition.getCustomID()).isNotNull();
            assertThat(customPosition.getPositionDateKind()).isNotNull();
            assertThat(customPosition.getInstrumentName()).isNotNull();
            assertThat(customPosition.getAssetType()).isNotNull();
            assertThat(customPosition.getFinInstrumentCode()).isNotNull();
            assertThat(customPosition.getIsin()).isNotNull();
            assertThat(customPosition.getPortfolioBrief()).isNotNull();
            assertThat(customPosition.getDepoStorageLocation()).isNotNull();
            assertThat(customPosition.getAccountClient()).isEqualTo(PortfolioStructureGenerator.BROK_ACCOUNT);
            assertThat(customPosition.getAccountFut()).isEqualTo(BrokAccountGenerator.DERIV_ACCOUNT);

            if (customPosition.getAssetType().equals(CustomPositionGenerator.ASSETTYPE_CUR_VALUE)) {
                assertThat(customPosition.getFinInstrumentCode()).isEqualTo(CurrencyGenerator.CURRENCY_BRIEF);
                assertThat(customPosition.getInstrumentName()).isEqualTo(CurrencyGenerator.CURRENCY_BRIEF);
                assertThat(customPosition.getIsin()).isEqualTo("");
                assertThat(customPosition.getTradingAcc()).isEqualTo("");

            } else if (customPosition.getAssetType().equals(CustomPositionGenerator.ASSETTYPE_DER_VALUE)) {
                assertThat(customPosition.getFinInstrumentCode()).isEqualTo(SecurityGenerator.SECURITY_CODE);
                assertThat(customPosition.getInstrumentName()).isEqualTo(SecurityGenerator.SECURITY_BRIEF);
                assertThat(customPosition.getIsin()).isEqualTo("");
                assertThat(customPosition.getTradingAcc()).isEqualTo("");
            } else {
                assertThat(customPosition.getFinInstrumentCode()).isEqualTo(SecurityGenerator.SECURITY_CODE);
                assertThat(customPosition.getInstrumentName()).isEqualTo(SecurityGenerator.SECURITY_NAME);
                assertThat(customPosition.getIsin()).isEqualTo(SecurityGenerator.ISIN);
                assertThat(customPosition.getTradingAcc()).isEqualTo(BrokAccountGenerator.TRADING_ACCOUNT);
            }
        }
    }
}
