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
@Table(name = "clntpos_client")
public class Client {

    @Id
    @Column(name = "clientid")
    private Long clientID;

    @Column(name = "clienttype")
    private Integer clientType;

    @Column(name = "externalnumber")
    private String externalNumber;

    @Column(name = "name")
    private String name;
}
