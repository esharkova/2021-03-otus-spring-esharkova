package ru.diasoft.micro.service.cache;

import ru.diasoft.micro.domain.Currency;

public interface CurrencyService {

    Currency findByISONumber(String isoNumber);

    Currency findByCurrencyBrief(String currencyBrief);

    Currency findByCurrencyId(Long currencyId);

    void delete(Long currencyId);

    Currency save(Currency currency);
}
