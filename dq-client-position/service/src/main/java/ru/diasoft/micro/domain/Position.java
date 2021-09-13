package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "clntpos_positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clntpos_positions_seq_id")
    @SequenceGenerator(name = "clntpos_positions_seq_id", sequenceName = "clntpos_positions_seq_id", allocationSize = 1)
    @Column(name = "positionid", unique = true)
    private Long positionID;

    @Column(name = "positiondatetime")
    private ZonedDateTime positionDateTime;

    @Column(name = "operationdate")
    private LocalDate operationDate;

    @Column(name = "positiondatekind")
    private Integer positionDateKind;

    @Column(name = "clientid")
    private Long clientID;

    @Column(name = "agreementid")
    private Long agreementID;

    @Column(name = "portfoliostructureid")
    private Long portfolioStructureID;

    @Column(name = "assetid")
    private Long assetID;

    @Column(name = "positiontype")
    private Integer positionType;

    @Column(name = "incomerest")
    private BigDecimal incomeRest;

    @Column(name = "income")
    private BigDecimal income;

    @Column(name = "expense")
    private BigDecimal expense;

    @Column(name = "outrest")
    private BigDecimal outRest;

    @Column(name = "fixpositiondate")
    private ZonedDateTime fixPositionDate;

    @Column(name = "fixflag")
    private Integer fixFlag;

    @Column(name = "fixuserid")
    private Long fixUserID;

    @Column(name = "market")
    private Integer market;

    @Column(name = "assettype")
    private Integer assetType;

    @Column(name = "tradePlace")
    private Integer tradePlace;

    @Column(name = "custody")
    private String custody;

    @Column(name = "depoacctype", nullable = false)
    private Integer depoAccType;


    public Position(Position previousPosition) {
        this.operationDate        = previousPosition.operationDate;
        this.clientID             = previousPosition.clientID;
        this.agreementID          = previousPosition.agreementID;
        this.assetID              = previousPosition.assetID;
        this.positionDateKind     = previousPosition.positionDateKind;
        this.positionType         = previousPosition.positionType;
        this.incomeRest           = previousPosition.incomeRest;
        this.fixFlag              = previousPosition.fixFlag;
        this.fixUserID            = previousPosition.fixUserID;
        this.positionDateTime     = previousPosition.positionDateTime;
        this.portfolioStructureID = previousPosition.portfolioStructureID;
        this.market               = previousPosition.market;
        this.tradePlace           = previousPosition.tradePlace;
        this.assetType            = previousPosition.assetType;
        this.custody              = previousPosition.custody;
        this.depoAccType          = previousPosition.depoAccType;
    }
}
