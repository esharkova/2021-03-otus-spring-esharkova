package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.Currency;

public class CurrencyGenerator {

    public static final long CURRENCY_ID = 1;
    public static final String ISO_NUMBER = "ISONumber";
    public static final String CURRENCY_NAME = "currencyName";
    public static final String CURRENCY_BRIEF = "currencyBrief";

    public static Currency getCurrency() {
        return Currency.builder()
                .currencyID(CURRENCY_ID)
                .ISONumber(ISO_NUMBER)
                .currencyName(CURRENCY_NAME)
                .currencyBrief(CURRENCY_BRIEF)
                .build();
    }
}
