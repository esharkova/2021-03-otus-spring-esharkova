package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.SendLimitDto;
import ru.diasoft.micro.util.LoggerUtils1;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@Primary
@Loggable
@Service
public class LimitSendingService {
    private final MessageSendingService messageSendingService;
    private final DailyLimitsServiceImpl dailyLimitsService;
    private final MessageSource messageSource;
    private static final DSLogger logger = DSLogManager.getLogger(LimitSendingService.class);

    public void parse(Object msg) {
      try {
          processMessageData(msg);
      } catch (ParseException e) {
          if (logger.isErrorEnabled())
              logger.error(messageSource.getMessage("operation.error.parseDate",
                      new Object[]{msg, e}, LocaleContextHolder.getLocale()));
      }
    }


    public void processMessageData(Object msg) throws ParseException {
        List<SendLimitDto> limitDtoList = dailyLimitsService.transform(msg);
        if(!limitDtoList.isEmpty()) {
            for (SendLimitDto sendLimit : limitDtoList) {
                messageSendingService.sendLimitMessage(sendLimit);
            }
        } else {
            LoggerUtils1.writeToLogInfo(logger, messageSource.getMessage("event.message.SendLimitIsEmpty",
                    new Object[]{msg}, LocaleContextHolder.getLocale()));
        }
    }
}
