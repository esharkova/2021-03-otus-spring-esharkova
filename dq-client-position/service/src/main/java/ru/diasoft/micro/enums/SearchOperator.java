package ru.diasoft.micro.enums;

import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Ops;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.diasoft.micro.exception.SearchOperatorUnknownException;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author mkushcheva
 * Операторы фильтрации для преобразования в предикаты
 */

@Getter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public enum SearchOperator {
    EQ("==", Ops.EQ),
    NE("!=", Ops.NE),
    GT(">", Ops.GT),
    LT("<", Ops.LT),
    GOE(">=", Ops.GOE),
    LOE("<=", Ops.LOE);

    public static SearchOperator getOperatorByExpression(String expression) throws SearchOperatorUnknownException {
        return Arrays.stream(values()).filter(searchOperator -> searchOperator.expression.equals(expression))
                .findFirst()
                .orElseThrow(() ->
                        new SearchOperatorUnknownException(MessageFormat.format("Недопустимое значение \"{0}\" оператора поиска(querydsl)", expression)));
    }

    private final String expression;
    private final Operator operator;
}
