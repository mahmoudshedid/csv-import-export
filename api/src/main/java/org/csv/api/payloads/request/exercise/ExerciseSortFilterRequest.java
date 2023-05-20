package org.csv.api.payloads.request.exercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csv.api.validations.annotations.ValidEnumValue;
import org.csv.domain.entities.Exercise;
import org.csv.domain.filter.OrderDirection;
import org.csv.domain.filter.exercise.ExerciseSortFilters;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseSortFilterRequest {

    @ApiModelProperty(notes = "Sort Exercises by fields", example = "ID")
    @ValidEnumValue(required = true, enumClass = Exercise.SortingFields.class)
    private String orderField = Exercise.SortingFields.ID.name();

    @ApiModelProperty(notes = "Sort Exercises by direction", example = "DESC")
    @ValidEnumValue(required = true, enumClass = OrderDirection.class)
    private String orderDirection = OrderDirection.DESC.name();

    public ExerciseSortFilters toEntity() {
        return ExerciseSortFilters.builder()
                .fields(Exercise.SortingFields.valueOf(orderField))
                .direction(OrderDirection.valueOf(orderDirection))
                .build();
    }
}
