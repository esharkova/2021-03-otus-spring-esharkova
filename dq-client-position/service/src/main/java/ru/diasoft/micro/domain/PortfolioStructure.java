package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "agreementid")
    private Long agreementID;

    @Column(name = "clientcode")
    private String clientCode;

    @Column(name = "market")
    private Integer market;

    @Column(name = "tradeplace")
    private String tradePlace;

    @Column(name = "tradeportfolioid")
    private Long tradePortfolioID;

    @Column(name = "tradeportfolio")
    private String tradePortfolio;

    @Column(name = "secsettaccount")
    private String secSettAccount;

    @Column(name = "brokaccount")
    private String brokAccount;

    @Column(name = "deposubaccount")
    private String depoSubAccount;
}