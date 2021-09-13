package ru.diasoft.micro.service.cache;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Agreement;
import ru.diasoft.micro.generator.AgreementGenerator;
import ru.diasoft.micro.repository.AgreementRepository;
import ru.diasoft.micro.service.cache.AgreementServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AgreementServiceImplTest {
    public static final String AGREEMENT_CODE = "agreementCode";

    @Mock
    private AgreementRepository agreementRepository;

    @InjectMocks
    private AgreementServiceImpl agreementService;

    Agreement createdAgreement;
    Agreement agreement;

    @BeforeEach
    void setUp() {

        agreement = AgreementGenerator.getAgreement();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByAgreementCodeTest() {
        when(agreementRepository.findByAgreementCode(AGREEMENT_CODE))
                .thenReturn(java.util.Optional.ofNullable(agreement));
        Agreement findAgreement = agreementService.findByAgreementCode(AGREEMENT_CODE);
        assertNotNull(findAgreement);
        assertEquals(findAgreement,agreement);
        verify(agreementRepository, times(1)).findByAgreementCode(AGREEMENT_CODE);
    }


    @Test
    void saveAgreementTest() {
        when(agreementRepository.save(any())).thenReturn(agreement);
        createdAgreement =  agreementService.save(agreement);
        assertEquals(createdAgreement, agreement);
        verify(agreementRepository, times(1)).save(any());
    }

    @Test
    void deleteAgreementTest() {
        when(agreementRepository.save(any())).thenReturn(agreement);
        createdAgreement =  agreementService.save(agreement);
        assertNotNull(createdAgreement);
        agreementService.delete(createdAgreement.getAgreementID());
        Agreement foundAgreement = agreementService.findByAgreementCode(createdAgreement.getAgreementCode());
        assertNull(foundAgreement);
    }
}