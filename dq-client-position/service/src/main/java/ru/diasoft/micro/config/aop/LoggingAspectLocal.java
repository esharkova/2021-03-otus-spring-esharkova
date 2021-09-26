package ru.diasoft.micro.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;

/**
 * @author mkushcheva
 * вычисляет время выполнения методов
 */

@Component
@Aspect
public class LoggingAspectLocal {
    private static final DSLogger logger = DSLogManager.getLogger(LoggingAspectLocal.class);

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long runningTime = System.currentTimeMillis() - startTime;

        logger.debug(joinPoint.getSignature() + " - Время работы: " +
                (Math.round((double) runningTime / (double) 1000))  + " секунд ("
                + runningTime + " миллисекунд).");
        return proceed;
    }
}
