/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author mkushcheva
 * JPA Entity для организации гибкой фильтрации апи
 * CLNTPOS_CustomPositions - позиция под пользователем.
 * для бизнеса важно, что бы пока пользователь работает с позицией, она не пересчитывалась.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DQCLNTPOSCustomPositions", description = "")
@JsonFilter("DynamicExclude")
@Entity
@Table(name = "clntpos_custompositions")
public class DQCLNTPOSCustomPositions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clntpos_custompositions_seq_id")
    @SequenceGenerator(name = "clntpos_custompositions_seq_id", sequenceName = "clntpos_custompositions_seq_id", allocationSize = 1)
    @Column(name = "custompositionid", unique = true)
    private Long customPositionID;

    @ApiModelProperty(name = "CalculationDateTime", dataType = "java.util.Date", value = "Дата и время начала расчета позиции с точностью до мс", required = false, position = 21)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "calculationdatetime")
    private ZonedDateTime calculationDateTime;

    @ApiModelProperty(name = "PositionDateKind", dataType = "Integer", value = "Тип позиции 1- если Т-1. Позиция по операциям с датой операции date = Т-1 относительно даты расчета positionDateTime без учета времени (дата Т).   2-если Т0. Позиция по операциям с датой операции date = Т относительно даты расчета positionDateTime без учета времени (дата Т).   3-если Т1. Позиция по операциям с датой операции date = Т+1 относительно даты расчета positionDateTime без учета времени (дата Т).   4-если Т2. Позиция по операциям с датой операции date = Т+2 относительно даты расчета positionDateTime без учета времени (дата Т).   5-если Тх. Позиция по операциям с датой операции date = Т+3 и более относительно даты расчета positionDateTime без учета времени (дата Т). ", required = false, position = 3)
    @Column(name = "positiondatekind")
    private Integer positionDateKind;

    @ApiModelProperty(name = "ClientAttr", dataType = "String", value = "ФИО клиента", required = false, position = 4)
    @Column(name = "clientattr")
    private String clientAttr;

    @ApiModelProperty(name = "AgreementNumber", dataType = "String", value = "Номер договора", required = false, position = 5)
    @Column(name = "agreementnumber")
    private String agreementNumber;

    @ApiModelProperty(name = "AccountClient", dataType = "String", value = "Номер брокерского счета", required = false, position = 6)
    @Column(name = "accountclient")
    private String accountClient;

    @ApiModelProperty(name = "AccountFut", dataType = "String", value = "Номер счета срочного рынка", required = false, position = 7)
    @Column(name = "accountfut")
    private String accountFut;

    @ApiModelProperty(name = "PortfolioBrief", dataType = "String", value = "Наименование портфеля", required = false, position = 8)
    @Column(name = "portfoliobrief")
    private String portfolioBrief;

    @ApiModelProperty(name = "TradingAcc", dataType = "String", value = "Место хранения (торгово клиринговый счет)", required = false, position = 9)
    @Column(name = "tradingacc")
    private String tradingAcc;

    @ApiModelProperty(name = "DepoStorageLocation", dataType = "String", value = "Название депозитария и раздела счета ДЕПО", required = false, position = 10)
    @Column(name = "depostoragelocation")
    private String depoStorageLocation;

    @ApiModelProperty(name = "InstrumentName", dataType = "String", value = "Наименование инструмента", required = false, position = 11)
    @Column(name = "instrumentname")
    private String instrumentName;

    @ApiModelProperty(name = "AssetType", dataType = "String", value = "Тип актива позиции", required = false, position = 12)
    @Column(name = "assettype")
    private String assetType;

    @ApiModelProperty(name = "FinInstrumentCode", dataType = "String", value = "Торговый код инструмента", required = false, position = 12)
    @Column(name = "fininstrumentcode")
    private String finInstrumentCode;

    @ApiModelProperty(name = "Isin", dataType = "String", value = "Наименование isin бумаги", required = false, position = 13)
    @Column(name = "isin")
    private String isin;

    @ApiModelProperty(name = "IncomeRest", dataType = "Float", value = "Входящий остаток", required = false, position = 14)
    @Column(name = "incomerest")
    private BigDecimal incomeRest;

    @ApiModelProperty(name = "OutRest", dataType = "Float", value = "Текущий остаток", required = false, position = 15)
    @Column(name = "outrest")
    private BigDecimal outRest;

    @ApiModelProperty(name = "Income", dataType = "Float", value = "Оборот по зачислениям", required = false, position = 16)
    @Column(name = "income")
    private BigDecimal income;

    @ApiModelProperty(name = "Expense", dataType = "Float", value = "Оборот по списаниям", required = false, position = 17)
    @Column(name = "expense")
    private BigDecimal expense;

    @ApiModelProperty(name = "CustomID", dataType = "Long", value = "Пользователь, под которым создали позицию", required = false, position = 1)
    @Column(name = "customid")
    private Long customID;

    @ApiModelProperty(name = "MoneyOvernightKind", dataType = "Integer", value = "Признак овернайта по денежным средствам на клиентском договоре: 1-установлен 2-не установлен", required = false, position = 18)
    @Column(name = "moneyovernightkind")
    private Integer moneyOvernightKind;

    @ApiModelProperty(name = "SecurityOvernightKind", dataType = "Integer", value = "Признак овернайта по ценным бумагам на клиентском договоре: 1-признак установлен; 2-признак не установлен.", required = false, position = 19)
    @Column(name = "securityovernightkind")
    private Integer securityOvernightKind;

    @ApiModelProperty(name = "MarginLendingKind", dataType = "Integer", value = "Признак маржинального кредитования на клиентском договоре обслуживания: 1-признак установлен; 2-признак не установлен.", required = false, position = 20)
    @Column(name = "marginlendingkind")
    private Integer marginLendingKind;
}