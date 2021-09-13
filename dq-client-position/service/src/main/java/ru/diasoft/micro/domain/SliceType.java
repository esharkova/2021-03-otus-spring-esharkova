package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author mkushcheva
 * типы срезов
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "clntpos_slicetypes")
public class SliceType implements Serializable {
    @Id
    @SequenceGenerator(name = "sliceTypes_seq_id", sequenceName = "clntpos_slicetypes_seq_id", allocationSize = 1)
    @GeneratedValue(generator = "sliceTypes_seq_id", strategy = GenerationType.SEQUENCE)
    @Column(name = "slicetypeid", nullable = false, unique = true)
    private Long sliceTypeID;

    @Column(name = "slicename", nullable = false)
    private String sliceName;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "customid", nullable = true)
    private Long customID;

    @Column(name = "priority", nullable = true)
    private Integer priority;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "slicetypeid")
    private List<SliceTypeValue> sliceTypeValues;
}
