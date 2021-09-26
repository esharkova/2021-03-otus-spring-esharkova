package ru.diasoft.micro.generator;

import ru.diasoft.micro.domain.Security;
import ru.diasoft.micro.domain.securityprofiledto.RestResponsePage;
import ru.diasoft.micro.domain.securityprofiledto.SecurityResponseDto;
import ru.diasoft.micro.domain.securityprofiledto.SecuritySearchParamDto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public static List<Security> getSecuritys() {
        List<Security> securitys = new LinkedList<>();
        securitys.add(Security.builder()
                .securityID(SECURITY_ID)
                .securityCode(SECURITY_CODE)
                .securityName(SECURITY_NAME)
                .securityBrief(SECURITY_BRIEF)
                .isin(ISIN)
                .securityType(SECURITY_TYPE)
                .tradeCode(TRADE_CODE)
                .build());
        return securitys;
    }

    public static SecuritySearchParamDto getSecuritySearchParamDtoByIsin() {
        List<String> isins = new LinkedList<>();
        isins.add(ISIN);
        return SecuritySearchParamDto.builder()
                .isinCodes(isins)
                .build();
    }

    public static List<Long> getSecurityIds(){
        List<Long> ids = new LinkedList<>();
        ids.add(SECURITY_ID);

        return ids;
    }

    public static SecuritySearchParamDto getSecuritySearchParamDtoByID() {
        return SecuritySearchParamDto.builder()
                .securityIds(getSecurityIds())
                .build();
    }

    public static RestResponsePage<SecurityResponseDto> getSecurityProfileResponse() {
        List<SecurityResponseDto> secResponseLst= new ArrayList<>();
        secResponseLst.add(
                SecurityResponseDto.builder()
                        .securityId(SECURITY_ID)
                        .securityType("1")
                        .build());
        return new RestResponsePage<>(secResponseLst);
    }

}
