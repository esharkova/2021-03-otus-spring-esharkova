package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.DQSlicePositionTypeValueList;
import ru.diasoft.micro.domain.SliceType;
import ru.diasoft.micro.domain.SliceTypeValue;

import java.util.ArrayList;
import java.util.List;

public class SliceTypeGenerator {
    private static final String SLICENNAME_TO = "Т0";
    private static final String SLICENNAME_T1 = "Т1";

    private static final Integer SLICETYPE_SYS = 1;
    private static final Integer SLICETYPE_CUSTOM = 2;

    public static final Long SLICETYPE_ID1 = 1L;
    public static final Long CUSTOM_ID1 = 1L;
    public static final Long CUSTOM_ID2 = 2L;

    private static final String SLICETYPEVALUENAME_POSDATEKIND= "positionDateKind";
    private static final String SLICETYPEVALUENAME_AGREEMENTCODE = "agreementNumber";
    private static final String SLICETYPEVALUENAME_CUSTOMID = "customID";
    private static final String SLICETYPEVALUENAME_CLIENTATTR = "clientAttr";

    private static final String SLICETYPEVALUE_POSDATEKIND_T0= "1";
    private static final String SLICETYPEVALUE_AGREEMENTCODE= "AgreementCodeValue";

    public static List<SliceType> getSliceTypeWithValueListForCustomPositionTest() {
        List<SliceType> result = new ArrayList<>();

        result.add(SliceType.builder()
                .sliceName(SLICENNAME_TO)
                .type(SLICETYPE_CUSTOM)
                .priority(1)
                .customID(CUSTOM_ID1)
                .sliceTypeValues(getSliceTypeValueList())
                .build());

        result.add(SliceType.builder()
                .sliceName(SLICENNAME_T1)
                .type(SLICETYPE_CUSTOM)
                .priority(15)
                .customID(CUSTOM_ID1)
                .sliceTypeValues(getSliceTypeValueList())
                .build());

        result.add(SliceType.builder()
                .sliceName(SLICENNAME_T1)
                .type(SLICETYPE_SYS)
                .priority(15)
                .customID(CUSTOM_ID1)
                .sliceTypeValues(getSliceTypeValueList())
                .build());

        result.add(SliceType.builder()
                .sliceName(SLICENNAME_T1)
                .type(SLICETYPE_CUSTOM)
                .priority(15)
                .customID(CUSTOM_ID2)
                .sliceTypeValues(getSliceTypeValueList())
                .build());


        return result;
    }

    public static SliceType getSliceTypeWithValueList() {
        return  SliceType.builder()
                .sliceTypeID(SLICETYPE_ID1)
                .sliceName(SLICENNAME_TO)
                .type(SLICETYPE_SYS)
                .customID(CUSTOM_ID2)
                .sliceTypeValues(getSliceTypeValueList())
                .build();
    }

    private static List<SliceTypeValue> getSliceTypeValueList() {
        List<SliceTypeValue> result = new ArrayList<>();

        result.add(SliceTypeValue.builder()
                .valueName(SLICETYPEVALUENAME_POSDATEKIND)
                .value("==("+SLICETYPEVALUE_POSDATEKIND_T0+")")
                .build());

        result.add(SliceTypeValue.builder()
                .valueName(SLICETYPEVALUENAME_AGREEMENTCODE)
                .value("==("+SLICETYPEVALUE_AGREEMENTCODE+")")
                .build());

        return result;
    }

    public static List<SliceTypeValue> getDefaultSliceTypeValueList() {
        List<SliceTypeValue> result = new ArrayList<>();

        result.add(SliceTypeValue.builder()
                .valueName(SLICETYPEVALUENAME_POSDATEKIND)
                .value("==("+SLICETYPEVALUE_POSDATEKIND_T0+")")
                .build());

        result.add(SliceTypeValue.builder()
                .valueName(SLICETYPEVALUENAME_CUSTOMID)
                .value("==(1005)")
                .build());

        result.add(SliceTypeValue.builder()
                .valueName(SLICETYPEVALUENAME_CLIENTATTR)
                .value("==(Иванов;Петрова;Сидоров)")
                .build());
        return result;
    }

    public static List<DQSlicePositionTypeValueList> getDefaultDQSliceTypeValueList() {
        List<DQSlicePositionTypeValueList> result = new ArrayList<>();
        result.add(new DQSlicePositionTypeValueList(
                SLICETYPEVALUENAME_POSDATEKIND,
                "==("+SLICETYPEVALUE_POSDATEKIND_T0+")"));

        result.add(new DQSlicePositionTypeValueList(
                SLICETYPEVALUENAME_AGREEMENTCODE,
                "==("+SLICETYPEVALUE_AGREEMENTCODE+")"));
        return result;    }
}
