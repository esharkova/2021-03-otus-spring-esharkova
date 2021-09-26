package ru.diasoft.micro.service.sliceposition;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.DQSlicePositionOutTypeID;
import ru.diasoft.micro.domain.DQSlicePositionTypeValueList;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.domain.SliceTypeValue;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mdp.lib.utils.exception.APIException;
import ru.diasoft.micro.service.DQSlicePositionService;

import java.util.LinkedList;
import java.util.List;


/**
 * @author mkushcheva
 * Сервис для работы с Объектом "Срез позиции"
 */
@RequiredArgsConstructor
@Loggable
@Service
@Primary
public class SlicePositionServiceImpl implements DQSlicePositionService {
    private final SliceTypeService sliceTypeService;
    private final MessageSource messageSource;

    private List<SliceTypeValue> dqSliceTypeValueListToSliceTypeValueList(List<DQSlicePositionTypeValueList> dQSlicePositionTypeValueList){
        List<SliceTypeValue> resultList = new LinkedList<>();
        dQSlicePositionTypeValueList.forEach(lst -> {
            resultList.add(SliceTypeValue.builder()
            .valueName(lst.getValueName())
            .value(lst.getValue()).build());
        });
        return resultList;
    }

    @Override
    public ResponseEntity<?> addDqslice(String sliceName, Integer type, Long userId, List<DQSlicePositionTypeValueList> dQSlicePositionTypeValueList) {
        if (!sliceTypeService.checkSliceType(type)) {
            throw new APIException(messageSource.getMessage("customPosSrv.message.IncorrectSliceType",
                    null, LocaleContextHolder.getLocale()));
        }

        SliceType sliceTypeSave = sliceTypeService.addSlice(
                sliceName,
                type,
                userId,
                dqSliceTypeValueListToSliceTypeValueList(dQSlicePositionTypeValueList));
        return ResponseEntity.status(HttpStatus.CREATED).body(new DQSlicePositionOutTypeID(sliceTypeSave.getSliceTypeID()));
    }

    @Override
    public ResponseEntity<?> deleteDqslice(Long sliceTypeId) {
        sliceTypeService.deleteById(sliceTypeId);
        return ResponseEntity.status(HttpStatus.OK).body("Successful delete");
    }

    @Override
    public ResponseEntity<?> getDqslicess(Pageable pageable, Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(sliceTypeService.findByCustomId(userId));
    }

    @Override
    public ResponseEntity<?> updateDqslice(Long sliceTypeId, String sliceName, Integer priority) {
        sliceTypeService.updateById(sliceTypeId, sliceName, priority);
        return ResponseEntity.status(HttpStatus.OK).body("Successful update");
    }
}
