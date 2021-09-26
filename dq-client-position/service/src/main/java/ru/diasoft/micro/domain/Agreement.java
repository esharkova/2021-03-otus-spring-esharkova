package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "moneyovernight")
    private Integer moneyOvernight;

    @Column(name = "securityovernight")
    private Integer securityOvernight;

    @Column(name = "marginlending")
    private Integer marginLending;

    @Column(name = "tradingaccbrief")
    private String tradingAccBrief;

    @Column(name = "clientid")
    private Long clientID;

    @Column(name = "clienttype")
    private Integer clientType;

    @Column(name = "clientname")
    private String clientName;

}
