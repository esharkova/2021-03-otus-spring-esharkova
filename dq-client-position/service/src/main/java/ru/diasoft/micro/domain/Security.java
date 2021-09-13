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
@Table(name = "clntpos_security")
public class Security {

    @Id
    @Column(name = "securityid")
    private Long securityID;

    @Column(name = "securitycode")
    private String securityCode;

    @Column(name = "securityname")
    private String securityName;

    @Column(name = "securitybrief")
    private String securityBrief;

    @Column(name = "isin")
    private String isin;

    @Column(name = "securitytype")
    private Integer securityType;

    @Column(name = "tradecode")
    private String tradeCode;

}
