package ru.diasoft.micro.service.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.diasoft.micro.domain.Currency;
import ru.diasoft.micro.generator.CurrencyGenerator;
import ru.diasoft.micro.repository.CurrencyRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CurrencyServiceImplTest {
    public static final String ISO_NUMBER = "ISONumber";

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    Currency currency;
    Currency createdCurrency;

    @BeforeEach
    void setUp() {
        currency = CurrencyGenerator.getCurrency();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByISONumberTest() {
        when(currencyRepository.findByISONumber(ISO_NUMBER))
                .thenReturn(java.util.Optional.ofNullable(currency));
        Currency findCurrency = currencyService.findByISONumber(ISO_NUMBER);
        assertNotNull(findCurrency);
        assertEquals(findCurrency,currency);
        verify(currencyRepository, times(1)).findByISONumber(ISO_NUMBER);
    }

    @Test
    void saveCurrencyTest() {
        when(currencyRepository.save(any())).thenReturn(currency);
        createdCurrency =  currencyService.save(currency);
        assertEquals(createdCurrency, currency);
        verify(currencyRepository, times(1)).save(any());
    }

    @Test
    void deleteCurrencyTest() {
        when(currencyRepository.save(any())).thenReturn(currency);
        createdCurrency =  currencyService.save(currency);
        assertNotNull(createdCurrency);
        currencyService.delete(createdCurrency.getCurrencyID());
        Currency foundCurrency = currencyService.findByISONumber(createdCurrency.getISONumber());
        assertNull(foundCurrency);
    }
}