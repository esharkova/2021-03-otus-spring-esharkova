package ru.diasoft.micro.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.Currency;
import ru.diasoft.micro.repository.CurrencyRepository;
import ru.diasoft.micro.service.cache.CurrencyService;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    @Cacheable(cacheNames="currency",key="{#ISONumber}")
    public Currency findByISONumber(String ISONumber) {
        return currencyRepository.findByISONumber(ISONumber).orElse(null);
    }

    @Override
    @Cacheable(cacheNames="currency",key="{#currencyBrief}")
    public Currency findByCurrencyBrief(String currencyBrief) {
        return currencyRepository.findByCurrencyBrief(currencyBrief).orElse(null);
    }

    @Override
    @Cacheable(cacheNames="currency",key="{#currencyId}")
    public Currency findByCurrencyId(Long currencyId) {
        return currencyRepository.findByCurrencyID(currencyId).orElse(null);
    }

    @Override
    @CacheEvict("currency")
    public void delete(Long currencyId) {
        currencyRepository.deleteById(currencyId);
    }

    @Override
    @CachePut("currency")
    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }
}
