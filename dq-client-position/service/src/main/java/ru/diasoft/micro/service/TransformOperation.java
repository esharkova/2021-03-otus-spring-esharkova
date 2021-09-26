package ru.diasoft.micro.service;

import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.model.PosOperationDto;
import ru.diasoft.micro.model.SendChangePositionDto;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author esharkova
 *
 * интерфейс Сервиса преобразования операции для позиции в изменение позиции.
 */
public interface TransformOperation {

    Position transformOperationToPosition(PosOperationDto msg);

    Boolean checkOperationDate(ZonedDateTime operationDate, ZonedDateTime currentDate);

    SendChangePositionDto transformPositionToDto(List<Position> positionList);

}
