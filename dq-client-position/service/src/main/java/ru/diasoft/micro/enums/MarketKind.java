package ru.diasoft.micro.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public enum MarketKind {
    MONEY("Валютный"),
    SECURITY("Фондовый");

    private final String value;
}
