package ru.diasoft.micro.service;

import ru.diasoft.micro.domain.Position;

import java.util.List;

/**
 * @author esharkova
 *
 * интерфейс Сервиса для расчета позиции.
 */
public interface CalculatePositionService {

    /**
     * метод поиска позиции по параметрам для последующего пересчета
     *
     * @param position   - изменение позиции, сформированное на основе полученной операции
     */
    Position findPositonByKeyParams(Position position);

    /**
     * метод сохранения позиции
     *
     * @param changePosition   - изменение позиции, сформированное на основе полученной операции
     */
    void savePosition(Position changePosition);

    /**
     * метод сохранения среза детализации позиции по дате, для последующего пересчета tx
     *
     * @param changePosition   - изменение позиции, сформированное на основе полученной операции
     */
    void saveDetailPosition(Position changePosition);

    /**
     * метод перерасчета атрибутов позиции
     *
     * @param previousPosition - предыдущая ранее рассчитанная позиция
     * @param changePosition   - изменение позиции, сформированное на основе полученной операции
     */
    Position recalculatePosition(Position previousPosition, Position changePosition);

    /**
     * метод перерасчета атрибутов следующих срезов(относительно даты) позиции
     *
     * @param createdPosition - рассчитанная позиция
     */
    List<Position> recalculateNextPosition(Position createdPosition);

    Position reCreatePosition(Position changePosition, Position previousPosition);

}
