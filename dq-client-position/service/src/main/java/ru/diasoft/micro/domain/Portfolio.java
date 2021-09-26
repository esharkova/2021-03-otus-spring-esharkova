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
@Table(name = "clntpos_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clntpos_portfolio_seq_id")
    @SequenceGenerator(name = "clntpos_portfolio_seq_id", sequenceName = "clntpos_portfolio_seq_id", allocationSize = 1)
    @Column(name = "portfolioid")
    private Long portfolioID;

    @Column(name = "brokaccount")
    private String brokAccount;

    @Column(name = "clearingplace")
    private String clearingPlace;

    @Column(name = "deposubaccounttype")
    private String depoSubAccountType;

    @Column(name = "tradeportfolio")
    private String tradePortfolio;

}
