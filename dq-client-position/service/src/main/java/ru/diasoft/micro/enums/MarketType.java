package ru.diasoft.micro.enums;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)

public enum MarketType {

    CBMARKET("1"), //биржа цб
    DRVMARKET("2"), //срочный
    CURRMARKET("3"), //валютный
    OTCMARKET("4"); //вне биржа

    private final String value;

}
