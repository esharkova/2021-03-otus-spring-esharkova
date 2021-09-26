package ru.diasoft.micro.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public enum RequestStatus {
    UNPROCESSED_REQUEST(1),  //Необработанный запрос
    DEFERRED_REQUEST(2),     //Отложенный запрос
    PROCESSED_REQUEST(3),    //Обработанный запрос
    ERROR_REQUEST(4);        //Ошибка при обработке запроса
    private final Integer value;
}
