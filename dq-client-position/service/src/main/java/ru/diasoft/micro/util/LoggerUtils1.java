package ru.diasoft.micro.util;

import ru.diasoft.digitalq.logging.DSLogger;

/**
 * @author mkushcheva
  */
public class LoggerUtils1 {
    private LoggerUtils1() {

    }

    /**
     * служебный метод для логирования Info Level
     *
     * @param logger - логгер
     * @param message   - сообщение
     */
    public static void writeToLogInfo(DSLogger logger, String message) {
        if (logger.isInfoEnabled()) {
            logger.info("{}", message);
        }
    }

    /**
     * служебный метод для логирования Error Level
     *
     * @param logger - логгер
     * @param message   - сообщение
     */
    public static void writeToLogError(DSLogger logger, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }
}
