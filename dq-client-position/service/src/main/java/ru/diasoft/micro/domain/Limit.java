package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "clntpos_limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "limits_positions_seq_id")
    @SequenceGenerator(name="limits_positions_seq_id", sequenceName = "clntpos_limits_positions_seq_id", allocationSize = 1)
    @Column(name="requestid", nullable = false, unique = true)
    private Long requestId;

    @Column(name = "requestdate")
    private ZonedDateTime requestDate;

    @Column(name= "objectpostype")
    private Integer objectPosType;

    @Column(name = "objectposnumber")
    private String objectPosNumber;

    @Column(name = "objectposid")
    private Long objectPosId;

    @Column(name = "requesttype")
    private Integer requestType;

    @Column(name = "singleaccflag")
    private Integer singleAccFlag;

    @Column(name = "clientcode")
    private String clientCode;

    @Column(name = "requeststatus")
    private Integer requestStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "requestid")
    private List<Market> marketList;
}
