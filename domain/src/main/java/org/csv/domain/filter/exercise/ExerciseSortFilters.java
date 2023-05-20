package org.csv.domain.filter.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.csv.domain.entities.Exercise;
import org.csv.domain.filter.OrderDirection;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class ExerciseSortFilters {
    @Builder.Default
    private Exercise.SortingFields fields = Exercise.SortingFields.ID;
    @Builder.Default
    private OrderDirection direction = OrderDirection.DESC;
}
