package ru.diasoft.micro.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeType {

    CODE_TYPE_TORG("Торговый код клиента"),
    CODE_TYPE_BROK("БрокСчет"),
    CODE_TYPE_PORTF("ПортфельДС");

    private final String value;
}
