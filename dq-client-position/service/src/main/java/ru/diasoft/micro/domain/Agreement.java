package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "clntpos_agreement")
public class Agreement {

    @Id
    @Column(name = "agreementid")
    private Long agreementID;

    @Column(name = "agreementcode")
    private String agreementCode;

    @Column(name = "bragrnumber")
    private String brAgrNumber;

    @Column(name = "brokaccfondnumber")
    private String brokAccFondNumber;

    @Column(name = "brokaccfutnumber")
    private String brokAccFutNumber;

    @Column(name = "futaccnumber")
    private String futAccNumber;

    @Column(name = "moneyovernight")
    private Integer moneyOvernight;

    @Column(name = "securityovernight")
    private Integer securityOvernight;

    @Column(name = "marginlending")
    private Integer marginLending;

    @Column(name = "tradingaccid")
    private Long tradingAccID;

    @Column(name = "tradingaccbrief")
    private String tradingAccBrief;

    @Column(name = "portfolioid")
    private Long portfolioID;

    @Column(name = "portfoliobrief")
    private String portfolioBrief;

    @Column(name = "clientid")
    private Long clientID;

    @Column(name = "clienttype")
    private Integer clientType;

    @Column(name = "clientname")
    private String clientName;

}
