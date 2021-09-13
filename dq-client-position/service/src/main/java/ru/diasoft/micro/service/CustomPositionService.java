package ru.diasoft.micro.service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.micro.domain.DQCLNTPOSCustomPositions;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.exception.FilterUnknownException;
import ru.diasoft.micro.lib.config.aop.Loggable;

import java.util.List;

/**
 * @author mkushcheva
 * <p>
 * интерфейс Сервиса для пользовательской позиции.
 */
public interface CustomPositionService {

    /**
     * метод заполнения таблицы-кэш для позиции пользовавателя
     * для бизнеса важно, что бы пока пользователь работает с позицией, она не пересчитывалась.
     *
     * @param customID   - Пользователь, под которым создаем позицию
     * @param sliceType - тип среза
     */
    void fillCustomPositionTableCache(Long customID, SliceType sliceType) throws FilterUnknownException;
}
