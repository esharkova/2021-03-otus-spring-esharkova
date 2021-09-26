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
@Table(name = "clntpos_brocaccount")
public class BrokAccount {
    @Id
    @Column(name = "agreementid")
    private Long agreementID;

    @Column(name = "brokaccount")
    private String brokAccount;

    @Column(name = "tradingaccount")
    private String tradingAccount;

    @Column(name = "derivaccount")
    private String derivAccount;

    @Column(name = "moneyovernight")
    private Integer moneyOvernight;

    @Column(name = "securityovernight")
    private Integer securityOvernight;

    @Column(name = "marginlending")
    private Integer marginLending;

    @Column(name = "uccmoex")
    private String uccMoEx;

}
