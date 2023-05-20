package org.csv.api.payloads.response.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csv.domain.entities.Exercise;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponse {

    private long id;
    private String source;
    private String codeListCode;
    private String code;
    private String displayValue;
    private String longDescription;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Long sortingPriority;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public ExerciseResponse(Exercise exercise) {
        this.id = exercise.getId();
        this.source = exercise.getSource();
        this.codeListCode = exercise.getCodeListCode();
        this.code = exercise.getCode();
        this.displayValue = exercise.getDisplayValue();
        this.longDescription = exercise.getLongDescription();
        this.fromDate = exercise.getFromDate();
        this.toDate = exercise.getToDate();
        this.sortingPriority = exercise.getId();
        this.createdBy = exercise.getCreatedBy();
        this.createdAt = exercise.getCreatedAt();
        this.updatedBy = exercise.getUpdatedBy();
        this.updatedAt = exercise.getUpdatedAt();
    }
}
