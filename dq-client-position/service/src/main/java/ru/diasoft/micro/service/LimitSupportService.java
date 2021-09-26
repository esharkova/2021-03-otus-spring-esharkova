package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.constants.MainConstants;
import ru.diasoft.micro.domain.Limit;
import ru.diasoft.micro.domain.Market;
import ru.diasoft.micro.enums.FixFlag;
import ru.diasoft.micro.enums.PositionDateKind;
import ru.diasoft.micro.enums.RequestStatus;
import ru.diasoft.micro.lib.config.aop.Loggable;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.model.MarketsDto;
import ru.diasoft.micro.repository.PositionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class LimitSupportService {
    private final PositionRepository positionRepository;

    // Cоздание лимита для сейва в базу
    public Limit limitBuilder(DailyLimitsDto dailyLimitsDto) throws ParseException {
        List<Market> marketList = new ArrayList<>();
        for (MarketsDto marketsDto:dailyLimitsDto.getMarkets()) {
            marketList.add(Market.builder()
                    .market(marketsDto.getMarket())
                    .trdAccCode(marketsDto.getTrdAccCode())
                    .build());
        }
        return Limit.builder()
                .requestDate(parseDate(dailyLimitsDto.getDateEvent()))
                .objectPosType(dailyLimitsDto.getObjectPosType())
                .objectPosNumber(dailyLimitsDto.getObjectPosNumber())
                .objectPosId(dailyLimitsDto.getObjectPosID())
                .requestType(dailyLimitsDto.getRequestType())
                .singleAccFlag(dailyLimitsDto.getSingleAccFlag())
                .clientCode(dailyLimitsDto.getClientCode())
                .requestStatus(getRequestStatus(dailyLimitsDto))
                .marketList(marketList)
                .build();
    }

    // Установка статуса
    private int getRequestStatus(DailyLimitsDto dailyLimitsDto) throws ParseException {
        int rsl = RequestStatus.DEFERRED_REQUEST.getValue();
        if(parseDate(dailyLimitsDto.getDateEvent())
                .equals(findDateForT0())) {
            rsl = RequestStatus.UNPROCESSED_REQUEST.getValue();
        }
        if(parseDate(dailyLimitsDto.getDateEvent())
                .isBefore(findDateForT0())) {
            rsl = RequestStatus.ERROR_REQUEST.getValue();
        }
        return rsl;
    }


    // изменение формата даты
    public ZonedDateTime parseDate(String date) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(MainConstants.DATE_FORMAT_FA);
        return formatter.parse(date).toInstant().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS);
    }

    // Поиск даты ,которая сейчас используется для расчета позиции по срезу Т0
    public ZonedDateTime findDateForT0() {
        return positionRepository.findByFixFlagAndPositionDateKind(FixFlag.NOT_FIXED.getValue(), PositionDateKind.T0.getValue())
                .stream().findFirst().get().getPositionDateTime().truncatedTo(ChronoUnit.DAYS);
    }
}
