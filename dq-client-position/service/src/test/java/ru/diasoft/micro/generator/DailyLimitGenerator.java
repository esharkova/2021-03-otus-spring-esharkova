package ru.diasoft.micro.generator;

import liquibase.pro.packaged.S;
import ru.diasoft.micro.domain.Limit;
import ru.diasoft.micro.domain.Market;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.model.MarketsDto;

import java.util.ArrayList;
import java.util.List;

public class DailyLimitGenerator {
    public static final long OBJECT_POS_ID = 123;
    public static final int POS_TYPE = 1;
    public static final int REQUEST_TYPE1 = 1;
    public static final int SINGLE_ACC_FLAG = 1;
    public static final String DATE_EVENT = "2021-09-14";
    public static final String CLIENT_CODE = "clientCode";
    public static final String MARKET = "Фондовый";
    public static final String CUR_MARKET = "Валютный";
    public static final String TRD_ACC = "trdAcc";
    public static final String POS_NUMBER = "posNumber";

    public static DailyLimitsDto getLimit() {
        return DailyLimitsDto.builder()
                .dateEvent(DATE_EVENT)
                .objectPosID(OBJECT_POS_ID)
                .objectPosType(POS_TYPE)
                .objectPosNumber(POS_NUMBER)
                .requestType(REQUEST_TYPE1)
                .singleAccFlag(SINGLE_ACC_FLAG)
                .clientCode(CLIENT_CODE)
                .markets(getMarket())
                .build();
    }

    private static List<MarketsDto> getMarket() {
        List<MarketsDto> markets = new ArrayList<>();
        markets.add(MarketsDto.builder()
                .market(MARKET)
                .trdAccCode(TRD_ACC)
                .build());
        markets.add(MarketsDto.builder()
                .market(CUR_MARKET)
                .trdAccCode(TRD_ACC)
                .build());
        return markets;
    }
}
