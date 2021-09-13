package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.Security;

public class SecurityGenerator {

    public static final long SECURITY_ID = 1;
    public static final String  SECURITY_CODE = "securCode";
    public static final String SECURITY_NAME = "securityName";
    public static final String SECURITY_BRIEF = "securityBrief";
    public static final String ISIN = "isin";
    public static final int SECURITY_TYPE = 1;
    public static final String TRADE_CODE = "tradeCode";

    public static Security getSecurity() {
        return Security.builder()
                .securityID(SECURITY_ID)
                .securityCode(SECURITY_CODE)
                .securityName(SECURITY_NAME)
                .securityBrief(SECURITY_BRIEF)
                .isin(ISIN)
                .securityType(SECURITY_TYPE)
                .tradeCode(TRADE_CODE)
                .build();
    }
}
