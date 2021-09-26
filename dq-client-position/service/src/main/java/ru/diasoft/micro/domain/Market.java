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
@Entity(name= "clntpos_markets")
@Table(name = "clntpos_markets")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "markets_positions_seq_id")
    @SequenceGenerator(name="markets_positions_seq_id", sequenceName = "clntpos_markets_positions_seq_id", allocationSize = 1)
    @Column(name="marketsid", nullable = false, unique = true)
    private Long marketsid;

    @Column(name= "market")
    private String market;

    @Column(name= "trdacccode")
    private String trdAccCode;

}
