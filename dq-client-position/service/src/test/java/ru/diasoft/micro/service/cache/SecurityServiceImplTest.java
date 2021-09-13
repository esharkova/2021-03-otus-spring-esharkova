package ru.diasoft.micro.service.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.generator.SecurityGenerator;
import ru.diasoft.micro.repository.SecurityRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SecurityServiceImplTest {
    public static final String SECURITY_CODE = "securCode";

    @Mock
    private SecurityRepository securityRepository;

    @InjectMocks
    private SecurityServiceImpl securityService;

    Security security;
    Security createdSecurity;

    @BeforeEach
    void setUp() {
        security = SecurityGenerator.getSecurity();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findBySecurityCodeTest() {
        when(securityRepository.findBySecurityCode(SECURITY_CODE))
                .thenReturn(java.util.Optional.ofNullable(security));
        Security findSecurity = securityService.findBySecurityCode(SECURITY_CODE);
        assertNotNull(findSecurity);
        assertEquals(findSecurity,security);
        verify(securityRepository, times(1)).findBySecurityCode(SECURITY_CODE);
    }

    @Test
    void saveSecurityTest() {
        when(securityRepository.save(any())).thenReturn(security);
        createdSecurity =  securityService.save(security);
        assertEquals(createdSecurity, security);
        verify(securityRepository, times(1)).save(any());
    }

    @Test
    void deleteSecurityTest() {
        when(securityRepository.save(any())).thenReturn(security);
        createdSecurity =  securityService.save(security);
        assertNotNull(createdSecurity);
        securityService.delete(createdSecurity.getSecurityID());
        Security foundSecurity = securityService.findBySecurityCode(createdSecurity.getSecurityCode());
        assertNull(foundSecurity);
    }

}