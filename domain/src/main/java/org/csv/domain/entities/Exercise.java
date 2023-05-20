package org.csv.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
public class Exercise extends BaseEntity {

    private String source;
    private String codeListCode;
    private String code;
    private String displayValue;
    private String longDescription;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer sortingPriority;

    public enum SortingFields {
        ID, NAME, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT
    }
}
