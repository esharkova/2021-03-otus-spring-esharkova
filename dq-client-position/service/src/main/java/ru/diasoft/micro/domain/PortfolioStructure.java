package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author mkushcheva
 * clntpos_portfStructure - Обогащенная кэш таблица с данными по структуре портфеля
 * Структура портфеля фиксирует логическую связь о клиента (ТКС) до портфеля, или даже до кода клиента.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "clntpos_portfoliostructure")
public class PortfolioStructure {

    @Id
    @Column(name = "portfoliostructureid")
    private Long portfolioStructureID;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "brokeragreementid")
    private Long brokerAgreementID;

    @Column(name = "subagreementid")
    private Long subAgreementID;

    @Column(name = "clientcode")
    private String clientCode;

    @Column(name = "market")
    private Integer market;

    @Column(name = "tradeplace")
    private Integer tradePlace;

    @Column(name = "tradeportfolio")
    private String tradePortfolio;

    @Column(name = "secsettaccount")
    private String secSettAccount;

    @Column(name = "brokaccount")
    private String brokAccount;

    @Column(name = "deposubaccount")
    private String depoSubAccount;

    @Column(name = "deposubaccounttype")
    private String depoSubAccountType;

    @Column(name = "clearingplace")
    private String clearingPlace;

}