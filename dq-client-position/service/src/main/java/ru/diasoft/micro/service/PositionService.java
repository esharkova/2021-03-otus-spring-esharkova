package ru.diasoft.micro.service;

import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.enums.FixFlag;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author esharkova
 * интерфейс работы с позицией
 */

public interface PositionService {
    /**
     * метод сохранения операции
     *
     * @param position - параметры позиции
     * @return сохраненная сущность позиции
     */

    Position createPosition(Position position);

    void deletePosition(Position position) ;

    List<Position> findByFixFlagAndPositionDateKind(Integer fixFlag, Integer positionDateKind);

    List<Position> findByFixFlagAndPositionDateKindNot(Integer fixFlag, Integer positionDateKind);

    List<Position> findByFixFlagAndPositionDateKindGreaterThan(Integer fixFlag, Integer positionDateKind);

    List<Position> savePositionList(List<Position> positionList);

    void setFixFlag(List<Long> positionIDList, Integer fixFlag, ZonedDateTime fixPositionDate);

    Integer getNextPositionDateKind(Integer positionDateKind);

    Integer getPreviousPositionDateKind(Integer positionDateKind);

    Boolean checkPositionDateKind(Integer positionDateKind);

}
