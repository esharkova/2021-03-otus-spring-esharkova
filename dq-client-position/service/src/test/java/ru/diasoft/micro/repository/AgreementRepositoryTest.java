package ru.diasoft.micro.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.generator.AgreementGenerator;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AgreementRepositoryTest {
    public static final String AGREEMENT_CODE = "agreementCode";

    @Autowired
    private AgreementRepository agreementRepository;

    Agreement createdAgreement;
    Agreement agreement;


    @BeforeEach
    void setUp() {
        agreement = AgreementGenerator.getAgreement();

        createdAgreement = agreementRepository.save(agreement);
    }

    @AfterEach
    void tearDown() {
        agreementRepository.delete(createdAgreement);
    }


    @Test
    void agreementCreateTest() {

        assertThat(createdAgreement).isEqualTo(agreement);

    }

    @Test
    void findByAgreementCodeTest() {
        Optional<Agreement> findAgreement = agreementRepository.findByAgreementCode(AGREEMENT_CODE);
        assertThat(findAgreement).isNotNull();
    }
}