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
import ru.diasoft.micro.model.BrokerAgreementDto;
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
    public static final String TRADING_ACC_BRIEF = "1_ТОРГ";
    public static final String TRADING_SYSTEM_FR = "Мосбиржа ФР";

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

    /*@Test
    void transformDtoToAgreementTest() {

        BrokerAgreementDto brokerAgreementDto = AgreementGenerator.BrokerAgreementDto();

        Agreement transformedAgreement = agreementService.transformDtoToAgreement(brokerAgreementDto);
        assertNotNull(transformedAgreement);
        assertEquals(transformedAgreement.getAgreementID(),brokerAgreementDto.getBrokerAgreementID());
        assertEquals(transformedAgreement.getAgreementCode(),brokerAgreementDto.getBrAgrNumber());
        assertEquals(transformedAgreement.getBrAgrNumber(),brokerAgreementDto.getBrAgrNumber());
        assertEquals(transformedAgreement.getTradingAccBrief(),brokerAgreementDto.getLinkedCodesAccounts().stream()
                .filter(a->a.getAccountLinkType().equals(TRADING_ACC_BRIEF)&a.getMarket().equals(TRADING_SYSTEM_FR))
                .findFirst().get().getAccountNumber());
        assertEquals(transformedAgreement.getClientID(),brokerAgreementDto.getClientID());
        assertEquals(transformedAgreement.getClientType(),brokerAgreementDto.getClientType());
        assertEquals(transformedAgreement.getClientName(),brokerAgreementDto.getClientName());

    }*/

}