package ru.diasoft.micro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.enrichment.SecurityEnrichment;
import ru.diasoft.micro.enums.AssetType;
import ru.diasoft.micro.generator.SecurityGenerator;
import ru.diasoft.micro.service.cache.CurrencyService;
import ru.diasoft.micro.service.cache.SecurityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@DisplayName("Сервис для работы с объектов Актив должен:")
public class AssetSecurityImplTest {
    private AssetSecurityImpl assetSecurityImpl;
    @Mock
    private SecurityService securityService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private SecurityEnrichment securityEnrichment;

    @BeforeEach
    void setUp() {
        assetSecurityImpl = new AssetSecurityImpl(securityService, currencyService, securityEnrichment);
    }

    @Test
    @DisplayName("Найти AssetID для ценной бумаги в кэш")
    public void getSecurityIDByIsinFromCashTest() {
        when(securityService.findByIsin(SecurityGenerator.ISIN)).thenReturn(SecurityGenerator.getSecurity());
        Long securityID = assetSecurityImpl.getAssetIDByAssetCode(SecurityGenerator.ISIN, AssetType.SECURITY);
        assertThat(securityID).isEqualTo(SecurityGenerator.SECURITY_ID);
        verify(securityService, times(1)).findByIsin(SecurityGenerator.ISIN);
    }

    @Test
    @DisplayName("Найти AssetID для ценной бумаги в Профиль ценной бумаги")
    public void getSecurityIDByIsinFromSecurityProfileTest() {
        when(securityService.findByIsin(SecurityGenerator.ISIN)).thenReturn(null);
        when(securityEnrichment.enrich(SecurityGenerator.ISIN)).thenReturn(SecurityGenerator.getSecurity());
        Long securityID = assetSecurityImpl.getAssetIDByAssetCode(SecurityGenerator.ISIN, AssetType.SECURITY);
        assertThat(securityID).isEqualTo(SecurityGenerator.SECURITY_ID);
        verify(securityService, times(1)).findByIsin(SecurityGenerator.ISIN);
        verify(securityEnrichment, times(1)).enrich(SecurityGenerator.ISIN);
    }
}
