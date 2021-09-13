package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.id.GUIDGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.lib.dqmessage.DQMessageGuid;
import ru.diasoft.micro.model.SendChangePositionDto;
import ru.diasoft.micro.sendchangeposition.publish.SendChangePositionPublishGateway;
import ru.diasoft.micro.lib.dqmessage.event.DQEventType;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MessageSendingServiceImpl implements MessageSendingService {
    private final SendChangePositionPublishGateway gateway;
    private static final DSLogger logger = DSLogManager.getLogger(MessageSendingServiceImpl.class);
    private final MessageSource messageSource;

    @Override
    public void sendChangePositionMessage(SendChangePositionDto sendChangePositionDto) {

        Message<SendChangePositionDto> message = MessageBuilder.withPayload(sendChangePositionDto)
                .setHeader(DQEventType.HEADER, DQEventType.AFTER_UPDATE)
                .build();
        gateway.sendChangePosition(message);

        logger.info(new StringBuilder(" Результат sendChangePositionDto: ")
                .append(message.toString())
                .toString());
    }
}
