package ru.diasoft.micro.enrichment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.feign.digitalmarketprofile.SecurityProfileClientFacade;
import ru.diasoft.micro.generator.SecurityGenerator;
import ru.diasoft.micro.repository.SecurityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("Класс обогащения ценной бумаги должен:")
public class SecurityEnrichmentTest {
    @Mock
    private SecurityProfileClientFacade securityProfileClientFacade;
    @Mock
    private SecurityRepository securityRepository;

    private SecurityEnrichment securityEnrichment;

    @BeforeEach
    void setUp() {
        securityEnrichment = new SecurityEnrichment(securityRepository, securityProfileClientFacade);
    }

    @Test
    @Disabled
    @DisplayName("Найти ценную бумагу в Профиле ценной бумаги и сохранить в базу")
    public void securityEnrichmentByIsinAndSaveDBtest() {
        when(securityProfileClientFacade.getSecuritiesBySearchParam(SecurityGenerator.getSecuritySearchParamDtoByIsin()))
                .thenReturn(SecurityGenerator.getSecuritys());
        when(securityRepository.save(any())).thenReturn(any());

        Security security = securityEnrichment.enrich(SecurityGenerator.ISIN);
        assertThat(security.getSecurityID()).isEqualTo(SecurityGenerator.SECURITY_ID);
        verify(securityProfileClientFacade, times(1))
                .getSecuritiesBySearchParam(SecurityGenerator.getSecuritySearchParamDtoByIsin());
        verify(securityRepository, times(1)).save(any());
    }

    @Test
    @Disabled
    @DisplayName("Найти по id ЦБ в Профиле ценной бумаги и сохранить в базу")
    public void securityEnrichmentBySecurityIDAndSaveDBtest() {
        when(securityProfileClientFacade.getSecuritiesBySearchParam(SecurityGenerator.getSecuritySearchParamDtoByID()))
                .thenReturn(SecurityGenerator.getSecuritys());
        when(securityRepository.saveAll(any())).thenReturn(any());

        securityEnrichment.enrich(SecurityGenerator.getSecurityIds());
        verify(securityProfileClientFacade, times(1))
                .getSecuritiesBySearchParam(SecurityGenerator.getSecuritySearchParamDtoByID());
        verify(securityRepository, times(1)).saveAll(any());
    }
}
