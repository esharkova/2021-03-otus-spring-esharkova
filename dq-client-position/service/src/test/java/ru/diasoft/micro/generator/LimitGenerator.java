package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.Limit;
import ru.diasoft.micro.domain.Market;
import ru.diasoft.micro.model.DailyLimitsDto;
import ru.diasoft.micro.model.MarketsDto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class LimitGenerator {
    public static final long OBJECT_POS_ID = 123;
    public static final int POS_TYPE = 1;
    public static final int REQUEST_TYPE1 = 1;
    public static final int SINGLE_ACC_FLAG = 1;
    public static final int OK_REQUEST = 1;
    public static final int FAIL_REQUEST = 4;
    public static final String CLIENT_CODE = "clientCode";
    public static final String MARKET = "Фондовый";
    public static final String CUR_MARKET = "Валютный";
    public static final String TRD_ACC = "trdAcc";
    public static final String POS_NUMBER = "posNumber";
    public static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.parse("2021-09-14T00:00+03:00[Europe/Moscow]");

    public static Limit getLimit() {
        return Limit.builder()
                .requestDate(ZONED_DATE_TIME)
                .objectPosId(OBJECT_POS_ID)
                .objectPosType(POS_TYPE)
                .objectPosNumber(POS_NUMBER)
                .requestType(REQUEST_TYPE1)
                .singleAccFlag(SINGLE_ACC_FLAG)
                .clientCode(CLIENT_CODE)
                .requestStatus(FAIL_REQUEST)
                .marketList(getMarket())
                .build();
    }

    public static Limit getOkLimit() {
        return Limit.builder()
                .requestDate(ZONED_DATE_TIME)
                .objectPosId(OBJECT_POS_ID)
                .objectPosType(POS_TYPE)
                .objectPosNumber(POS_NUMBER)
                .requestType(REQUEST_TYPE1)
                .singleAccFlag(SINGLE_ACC_FLAG)
                .clientCode(CLIENT_CODE)
                .requestStatus(OK_REQUEST)
                .marketList(getMarket())
                .build();
    }

    private static List<Market> getMarket() {
        List<Market> markets = new ArrayList<>();
        markets.add(Market.builder()
                .market(MARKET)
                .trdAccCode(TRD_ACC)
                .build());
        markets.add(Market.builder()
                .market(CUR_MARKET)
                .trdAccCode(TRD_ACC)
                .build());
        return markets;
    }
}
