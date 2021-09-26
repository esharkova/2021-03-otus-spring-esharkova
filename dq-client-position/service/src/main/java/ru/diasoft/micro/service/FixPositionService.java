package ru.diasoft.micro.service;

import ru.diasoft.micro.domain.Position;

import java.util.List;

/**
 * @author esharkova
 *
 * интерфейс Сервиса фиксации позиции.
 */
public interface FixPositionService {

    /**
     * метод поиска записей позиции, подлежащих фиксации
     */
    List<Position> findNotFixPosition();

    /**
     * метод фиксации позиции
     */
    void fixPosition();

}
