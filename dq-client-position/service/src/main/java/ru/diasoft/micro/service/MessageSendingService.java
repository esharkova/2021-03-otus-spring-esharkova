package ru.diasoft.micro.service;

import org.springframework.messaging.MessageHeaders;
import ru.diasoft.micro.model.SendChangePositionDto;
import ru.diasoft.micro.model.SendLimitDto;

/**
        * @author esharkova
        * сервис отправки сообщений
        */
public interface MessageSendingService {

    /**
     * Метод отправки изменений позици
     *
     * @param sendChangePositionDto - изменения позиции
     */
    void sendChangePositionMessage(SendChangePositionDto sendChangePositionDto);

    void sendLimitMessage(SendLimitDto sendLimitDto);
}
