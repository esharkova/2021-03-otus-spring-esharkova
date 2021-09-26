package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

/**
 * @author mkushcheva
 * значение типа среза
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "clntpos_slicetypevalue")
@Table(name = "clntpos_slicetypevalue")
public class SliceTypeValue implements Serializable {

    @Id
    @SequenceGenerator(name = "sliceTypeValue_seq_id", sequenceName = "clntpos_sliceTypeValue_seq_id", allocationSize = 1)
    @GeneratedValue(generator = "sliceTypeValue_seq_id", strategy = GenerationType.SEQUENCE)
    @Column(name = "slicetypevalueid", nullable = false, unique = true)
    private Long sliceTypeValueID;

    @Column(name = "valuename", nullable = false)
    private String valueName;

    @Column(name = "value", nullable = false)
    private String value;

    public String getFilterName() {
        return valueName.toLowerCase(Locale.ROOT);
    }

    public String getFilterExp() {
         return value.substring(0, value.indexOf('('));
    }

    public String getFilterValue() {
        return value.substring(value.indexOf('(')+1, value.indexOf(')'));
    }
}
