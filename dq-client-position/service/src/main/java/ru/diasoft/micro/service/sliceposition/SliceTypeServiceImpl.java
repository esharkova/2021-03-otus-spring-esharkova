package ru.diasoft.micro.service.sliceposition;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.config.aop.LogExecutionTime;
import ru.diasoft.micro.constants.PositionConst;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.domain.SliceTypeValue;
import ru.diasoft.micro.enums.SearchOperator;
import ru.diasoft.micro.enums.SliceTypeEnum;
import ru.diasoft.micro.exception.FilterTypeUnknownException;
import ru.diasoft.micro.exception.FilterUnknownException;
import ru.diasoft.micro.exception.SearchOperatorUnknownException;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.repository.SliceTypeRepository;
import ru.diasoft.micro.util.LoggerUtils1;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SliceTypeServiceImpl implements SliceTypeService {
    private static final DSLogger logger = DSLogManager.getLogger(SliceTypeServiceImpl.class);
    private final MessageSource messageSource;
    private final SliceTypeRepository repository;

    @Loggable
    @Override
    @LogExecutionTime
    public Predicate getSearchCondition(List<SliceTypeValue> filters) throws SearchOperatorUnknownException, FilterUnknownException, FilterTypeUnknownException {
        BooleanBuilder searchCondition = new BooleanBuilder();
        if (filters != null) {
            for (SliceTypeValue filter : filters) {
                Operator operator = SearchOperator.getOperatorByExpression(filter.getFilterExp()).getOperator();
                Path<?> path = parsePath(filter.getFilterName());
                searchCondition.and(getFilterValue(operator, path, filter.getFilterValue()));
            }
        } else {
            LoggerUtils1.writeToLogError(logger, messageSource.getMessage("customPosSrv.message.PositionSearchFiltersIsNull",
                    null, LocaleContextHolder.getLocale()));
        }
        return searchCondition;
    }

    private BooleanBuilder getFilterValue(Operator operator, Path<?> path, String value) throws FilterTypeUnknownException {
        BooleanBuilder multiplePredicate = new BooleanBuilder();
        //Если есть знак ";" то это список значений
        String[] values = value.split(";");
        //Для каждого сформируем свой предикат и объединим их через or
        for (String vl : values) {
            BooleanExpression predicate = getPredicate(operator, path, vl);
            multiplePredicate.or(predicate);
        }
        return multiplePredicate;
    }

    @Loggable
    @Override
    public Integer getNextPriority() {
        return repository.findMaxPriority() + 1;
    }

    @Loggable
    @Override
    public Boolean checkSliceType(Integer type) {
        return Arrays.stream(SliceTypeEnum.values()).anyMatch(element -> element.getValue() == type);
    }

    @Loggable
    @Override
    public SliceType addSlice(String sliceName, Integer type, Long userId, List<SliceTypeValue> valueList) {
        SliceType sliceType = SliceType.builder()
                .sliceName(sliceName)
                .type(type)
                .customID(userId)
                .sliceTypeValues(valueList)
                .priority(getNextPriority())
                .build();
        return repository.save(sliceType);
    }

    @Loggable
    @Override
    public void deleteById(Long sliceTypeId) {
        repository.deleteById(sliceTypeId);
    }

    @Loggable
    @Override
    public void updateById(Long sliceTypeId, String sliceName, Integer priority) {
        if (sliceName != null && priority == null) {
            repository.updateSliceNameByID(sliceTypeId, sliceName);
        } else if (sliceName == null && priority != null) {
            repository.updateSlicePriorityByID(sliceTypeId, priority);
        } else {
            repository.updateSliceNameAndPriorityByID(sliceTypeId, sliceName, priority);
        }
    }

    @Loggable
    @Override
    public List<SliceType> findByCustomId(Long customId) {
        return repository.findByCustomIDAndSystemSlice(customId);
    }


    private Path<?> parsePath(String filterName) throws FilterUnknownException {
        Path<?> path = PositionConst.globalSearchAttributes.get(filterName);
        if (path == null) {
            throw new FilterUnknownException(MessageFormat.format("Не поддерживаемый фильтр:  \"{0}\" .)", filterName));
        }
        return path;
    }

    private BooleanExpression getPredicate(Operator operator, Path<?> path, String value) throws FilterTypeUnknownException {
        if (path instanceof StringPath) {
            Expression<String> constant = Expressions.constant(value);
            return Expressions.predicate(operator, path, constant);
        } else if (path instanceof NumberPath) {
            Expression<BigDecimal> constant = Expressions.constant(new BigDecimal(value));
            return Expressions.predicate(operator, path, constant);
        } else if (path instanceof DateTimePath) {
            Expression<ZonedDateTime> constant = Expressions.constant(ZonedDateTime.parse(value));
            return Expressions.predicate(operator, path, constant);
        } else {
            throw new FilterTypeUnknownException(MessageFormat.format("Не поддерживаемый тип фильтра:  \"{0}\" .)", path.getClass()));
        }
    }
}
