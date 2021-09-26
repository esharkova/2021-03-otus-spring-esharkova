package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "CLNTPOS_ModelOperations")
public class ModelOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clntpos_model_operation_seq_id")
    @SequenceGenerator(name="clntpos_model_operation_seq_id", sequenceName = "clntpos_model_operation_seq_id", allocationSize = 1)
    @Column(name = "OperationID", unique = true)
    private Long operationID;

    @Column(name = "CounterpartCode")
    private String counterpartCode;

    @Column(name = "ClientCode")
    private String clientCode;

    @Column(name = "Direction")
    private Integer direction;

    @Column(name = "SecurityCode")
    private String securityCode;

    @Column(name = "ISONumber")
    private String isoNumber;

    @Column(name = "Quantity")
    private BigDecimal quantity;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "OperationNumber", nullable = false)
    private String operationNumber;

    @Column(name = "OperationDate", nullable = false)
    private ZonedDateTime operationDate;

}
