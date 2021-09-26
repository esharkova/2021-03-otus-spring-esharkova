package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.config.aop.LogExecutionTime;
import ru.diasoft.micro.constants.PositionConst;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.domain.SliceTypeValue;
import ru.diasoft.micro.exception.FilterUnknownException;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.repository.CustomPositionRepository;

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author mkushcheva
 * Сервис для пользовательской позиции.
 *
 */
@Service
@RequiredArgsConstructor
public class CustomPositionServiceImpl implements CustomPositionService{
    private final CustomPositionRepository customPositionRepository;
    private static final DSLogger logger = DSLogManager.getLogger(CustomPositionServiceImpl.class);

    @Loggable
    @Transactional
    @Override
    @LogExecutionTime
    public void fillCustomPositionTableCache(Long customID, SliceType sliceType) throws FilterUnknownException {
        Integer positionDateKind = getPositionDateKind(sliceType.getSliceTypeValues());
        //Удалить существующую позицию из кэш
        customPositionRepository.deleteByCustomIDAndPositionDateKind(customID,positionDateKind);
        //Поиск и сохранения позиции в КЭШ
        customPositionRepository.insertPositionIntoCustomPosition(positionDateKind, customID, ZonedDateTime.now());
    }

    private Integer getPositionDateKind(List<SliceTypeValue> filters) throws FilterUnknownException {
        SliceTypeValue value = filters.stream()
                .filter(c -> c.getValueName().equals(PositionConst.MAINFILTERNAME))
                .findFirst().orElseThrow(()->
                        new FilterUnknownException(MessageFormat.format("Не задан обязательный параметр поиска позиции:  \"{0}\" . )", PositionConst.MAINFILTERNAME)));
        return NumberUtils.toInt (value.getFilterValue(), 1);
    }
}
