package org.csv.domain.filter.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseFilters {
    private String source;
    private String codeListCode;
    private String code;
    private String displayValue;
    private String longDescription;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long sortingPriority;
    private String createdBy;
    private String updatedBy;
}
