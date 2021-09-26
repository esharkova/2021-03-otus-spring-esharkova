package ru.diasoft.micro.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
/**
 * 1 - Сделка покупки/продажи ЦБ (Т0, Т+)
 * 2 - Сделка РЕПО
 * 3 - Сделка СВОП
 * 4 - Сделка наличная (TOD, TOM, SPOT)
 * 5 - Сделка СВОП-контракт
 * 6 - Сделка с фьючерсом
 * 7 - Сделка с опционом
 * 8 - Сделка с форвардом
 * 9 - Сделка МБК
 * 14 - Комиссии биржи
 * 15 - Комиссии брокера по сделке
 * 17 - Выплаты по корпоративным событиям
 * 18 - Глобальные операции с ЦБ
 * 20 - Внебиржевые займы ЦБ (в т.ч. для маржинально кредитования)
 * 21 - Операции с КСУ (клиринговыми сертификатами участия)*/
public enum OperationKind {
    BUYSELL_DEAL(1, "Deal"),
    REPO_DEAL(2, "Deal"),
    SWAP_DEAL (3, "Deal"),
    CURRENCY_DEAL(4, "Deal"),
    SWAP_CONTACT_DEAL (5, "Deal"),
    FUTURE_DEAL (6, "Deal"),
    OPTION_DEAL (7, "Deal"),
    FORWARD_DEAL (8, "Deal"),
    MBK_DEAL (9, "Deal"),

    BIRG_COMMISSION (14, "Deal"),
    BROK_COMMISSION (15, "Deal"),

    CORP_EVENT(16, "DepoOperation"),
    GLOBAL_OPERATION (17, "DepoOperation"),


    lOAN (20, "Deal"),
    CLEARING_CERTIFICATE_OPERATION(21, "Deal")
    ;

    private final Integer value;
    private final String type;


}
