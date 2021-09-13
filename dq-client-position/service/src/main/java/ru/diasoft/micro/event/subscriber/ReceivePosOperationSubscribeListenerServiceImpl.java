package ru.diasoft.micro.event.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.logging.DSLogManager;
import ru.diasoft.digitalq.logging.DSLogger;
import ru.diasoft.micro.domain.Position;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.PosOperationDto;
import ru.diasoft.micro.receiveposoperation.subscribe.ReceivePosOperationSubscribeListenerService;
import ru.diasoft.micro.service.CalculatePositionService;
import ru.diasoft.micro.service.TransformOperation;

import java.time.ZonedDateTime;

@Service
@Primary
@RequiredArgsConstructor
@Loggable
public class ReceivePosOperationSubscribeListenerServiceImpl implements ReceivePosOperationSubscribeListenerService {

    private final CalculatePositionService calculatePositionService;
    private final TransformOperation transformOperation;
    private static final DSLogger logger = DSLogManager.getLogger(ReceivePosOperationSubscribeListenerServiceImpl.class);


    @Override
    public void receivePosOperation(PosOperationDto msg) {

        if (transformOperation.checkOperationDate(msg.getFlowsSettleDate(), ZonedDateTime.now())){

        Position changePosition = transformOperation.transformOperationToPosition(msg);

        calculatePositionService.savePosition(changePosition);
        }
        else logger.info(new StringBuilder("Операция не будет учтена в позиции: ")
                .append(msg)
                .toString());
    }

}
