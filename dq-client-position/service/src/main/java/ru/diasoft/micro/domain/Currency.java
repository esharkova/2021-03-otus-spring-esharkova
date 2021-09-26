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
@Table(name = "clntpos_currency")
public class Currency {
    
    @Id
    @Column(name = "currencyid")
    private Long currencyID;

    @Column(name = "isonumber")
    private String ISONumber;

    @Column(name = "currencyname")
    private String currencyName;

    @Column(name = "currencybrief")
    private String currencyBrief;

}
