package ru.diasoft.micro.domain.securityprofiledto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityResponseDto implements Serializable {
    @JsonProperty("securityId")
    @NotNull
    private Long securityId;

    @JsonProperty("dataSource")
    private Integer dataSource;

    @JsonProperty("dataSourceName")
    private String dataSourceName;

    @JsonProperty("sourceCode")
    private String sourceCode;

    @JsonProperty("securityType")
    private String securityType;

    @JsonProperty("securityTypeName")
    private String securityTypeName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("enName")
    private String enName;

    @JsonProperty("cfiCode")
    private String cfiCode;

    @JsonProperty("isinCode")
    private String isinCode;

    @JsonProperty("nsdCode")
    private String nsdCode;

    @JsonProperty("issueSizePlanned")
    private BigDecimal issueSizePlanned;

    @JsonProperty("issuedSize")
    private BigDecimal issuedSize;

    @JsonProperty("issuedCurrentSize")
    private BigDecimal issuedCurrentSize;

    @JsonProperty("issuerId")
    private Long issuerId;

    @JsonProperty("issuerDataSource")
    private Integer issuerDataSource;

    @JsonProperty("issuerSourceCode")
    private String issuerSourceCode;

    @JsonProperty("issuerName")
    private String issuerName;

    @JsonProperty("issuerBrief")
    private String issuerBrief;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("offerDateStart")
    private ZonedDateTime offerDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("offerDateEnd")
    private ZonedDateTime offerDateEnd;

    @JsonProperty("regAuthoritySourceCode")
    private String regAuthoritySourceCode;

    @JsonProperty("regAuthorityName")
    private String regAuthorityName;

    @JsonProperty("regAuthorityId")
    private Long regAuthorityId;

    @JsonProperty("cbTypeCode")
    private Integer cbTypeCode;

    @JsonProperty("cbTypeName")
    private String cbTypeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("stateRegDate")
    private ZonedDateTime stateRegDate;

    @JsonProperty("stateRegNumber")
    private String stateRegNumber;

    @JsonProperty("issueNumber")
    private String issueNumber;

    @JsonProperty("issueForm")
    private Integer issueForm;

    @JsonProperty("issueFormName")
    private String issueFormName;

    @JsonProperty("registarHolderDataSource")
    private Integer registarHolderDataSource;

    @JsonProperty("registarHolderSourceCode")
    private String registarHolderSourceCode;

    @JsonProperty("registarHolderName")
    private String registarHolderName;

    @JsonProperty("registarHolderId")
    private Long registarHolderId;

    @JsonProperty("incPawnshopList")
    private Boolean incPawnshopList;

    @JsonProperty("forQualifiedInvestors")
    private Boolean forQualifiedInvestors;

    @JsonProperty("facialAccountType")
    private Integer facialAccountType;

    @JsonProperty("facialAccountTypeName")
    private String facialAccountTypeName;

    @JsonProperty("facialAccountNumber")
    private String facialAccountNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonProperty("facialAccountOpenDate")
    private ZonedDateTime facialAccountOpenDate;

    @JsonProperty("depositorySourceCode")
    private String depositorySourceCode;

    @JsonProperty("depositoryDataSource")
    private Integer depositoryDataSource;

    @JsonProperty("depositoryId")
    private Long depositoryId;

}
