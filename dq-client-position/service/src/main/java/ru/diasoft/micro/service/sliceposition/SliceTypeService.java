package ru.diasoft.micro.service.sliceposition;

import ru.diasoft.micro.domain.DQSlicePositionTypeValueList;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.domain.SliceTypeValue;

import java.util.Arrays;
import java.util.List;
import com.querydsl.core.types.Predicate;
import ru.diasoft.micro.enums.SliceTypeEnum;
import ru.diasoft.micro.exception.FilterTypeUnknownException;
import ru.diasoft.micro.exception.FilterUnknownException;
import ru.diasoft.micro.exception.SearchOperatorUnknownException;

/**
 * @author mkushcheva
 * интерфейс Сервиса для типа среза.
 */
public interface SliceTypeService {
    /**
     * метод формирования условий для поиска позиции по фильтрам из типа среза
     *
     * @param filters  - значения типа среза - фильтры
     * @return фильтры типа Predicate
     */
    Predicate getSearchCondition(List<SliceTypeValue> filters) throws SearchOperatorUnknownException, FilterUnknownException, FilterTypeUnknownException;

    /**
     * метод получения приоритета
     *
     * @return приоритет
     */
    Integer getNextPriority();

    /**
     * метод проверки типа среза.
     */
    Boolean checkSliceType(Integer type);

    /**
     * метод добавления типа среза.
     * @param sliceName  - наименование
     * @param type  - тип - пользовательский/системный
     * @param userId  - пользователь
     * @param valueList  - значения типа среза
     * @return тип среза
     */
    SliceType addSlice(String sliceName, Integer type, Long userId, List<SliceTypeValue> valueList);

    /**
     * метод добавления типа среза.
     * @param sliceTypeId  - id типа среза
     * @return тип среза
     */
    void deleteById(Long sliceTypeId);

    /**
     * метод обновления типа среза.
     * @param sliceTypeId  - id типа среза
     * @param sliceName  - имя типа среза
     * @param priority  - приоритет типа среза
     * @return тип среза
     */
    void updateById(Long sliceTypeId, String sliceName, Integer priority);

    /**
     * метод Получение среза позиции для интерфейса
     * @param customId  - пользователь
     * @return тип среза
     */
    List<SliceType> findByCustomId(Long customId);

}
