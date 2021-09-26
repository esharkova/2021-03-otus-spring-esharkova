package ru.diasoft.micro.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.config.aop.LogExecutionTime;
import ru.diasoft.micro.domain.DQCLNTPOS;
import ru.diasoft.micro.domain.DQCLNTPOSCreate;
import ru.diasoft.micro.domain.DQCLNTPOSIncomeMargDeal;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.exception.FilterTypeUnknownException;
import ru.diasoft.micro.exception.FilterUnknownException;
import ru.diasoft.micro.exception.SearchOperatorUnknownException;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.mdp.lib.utils.exception.APIException;
import ru.diasoft.micro.repository.CustomPositionRepository;
import ru.diasoft.micro.repository.SliceTypeRepository;
import ru.diasoft.micro.service.sliceposition.SliceTypeService;

/**
 * @author mkushcheva
 * Сервис для работы с Объектом "Позиция клиента"
 */

@RequiredArgsConstructor
@Service
@Primary
@Loggable
public class ClientPositionServiceImpl implements DQClientPositionService{
    private final CustomPositionRepository customPositionRepository;
    private final CustomPositionService customPositionService;
    private final SliceTypeRepository sliceTypeRepository;
    private final SliceTypeService sliceTypeService;

    @Override
    public ResponseEntity<?> addDQCLNTPOS(DQCLNTPOSCreate dQCLNTPOSCreate) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteDQCLNTPOS(Long paramId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDQCLNTPOSId(Long paramId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDQCLNTPOSs(DQCLNTPOS predicate, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDqclientmodelcustompositions(Predicate predicate, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDqmodeldealclntposs(Pageable pageable, DQCLNTPOSIncomeMargDeal dQCLNTPOSIncomeMarDeal) {
        return null;
    }

    /**
     * метод Получения полной позиции по переданным срезам позиции. Полученную позицию сохраняем в кэш
     *
     * @param pageable   - страница
     * @param marginDeal - Выводить позицию с учетом модельных сделок 1 - без учета, 2 - с учетом
     * @param customId   - Пользователь, под которым создаем позицию
     * @param sliceTypeId - тип среза
     * @return поцизия для пользователя
     */
    @Override
    @LogExecutionTime
    public ResponseEntity<?> getDqclntpositionslice(Pageable pageable, Long customId, Long sliceTypeId, Integer marginDeal) {
        BooleanBuilder searchCondition = new BooleanBuilder();
        if (sliceTypeId != null) {
            try{
                SliceType sliceType = sliceTypeRepository.findById(sliceTypeId).get();
                customPositionService.fillCustomPositionTableCache(customId, sliceType);
                //сформировать фильтры
                searchCondition.and(sliceTypeService.getSearchCondition(sliceType.getSliceTypeValues()));
            } catch ( FilterUnknownException | SearchOperatorUnknownException | FilterTypeUnknownException exp) {
                throw new APIException("При заполнении таблицы-кэш возникли ошибки:" + exp);
            }
        }
        //Вывести отфильтрованные данные
        return ResponseEntity.status(HttpStatus.OK).body(customPositionRepository.findAll(searchCondition, pageable));
    }

    /**
     * метод Получения позиции клиента для интерфейса без учета модельных сделок. Данные из КЭШ
     *
     * @param predicate  - параметры для фильтарции тип данных DQCLNTPOSCustomPositions
     * @param pageable   - страница
     * @return поцизия для пользователя
     */
    @Override
    @LogExecutionTime
    public ResponseEntity<?> getDqclientcustompositions(Predicate predicate, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customPositionRepository.findAll(predicate, pageable));
    }

    @Override
    public ResponseEntity<?> updateDQCLNTPOS(Long paramId, DQCLNTPOS dQCLNTPOS) {
        return null;
    }
}
