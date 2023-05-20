package org.csv.domain.usecases.exercise;

import lombok.RequiredArgsConstructor;
import org.csv.domain.entities.Exercise;
import org.csv.domain.repository.ExerciseRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateExerciseUseCase {

    private final ReadExerciseUseCase readExerciseUseCase;
    private final ExerciseRepository exerciseRepository;

    public Exercise execute(Exercise exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("Exercise cannot be null");
        }
        Exercise currentExercise = this.readExerciseUseCase.execute(exercise.getId());

        Exercise toUpdateExercise = currentExercise.toBuilder()
                .source(exercise.getSource())
                .codeListCode(exercise.getCodeListCode())
                .code(exercise.getCode())
                .displayValue(exercise.getDisplayValue())
                .longDescription(exercise.getLongDescription())
                .fromDate(exercise.getFromDate())
                .toDate(exercise.getToDate())
                .sortingPriority(exercise.getSortingPriority())
                .build();

        return this.exerciseRepository.save(toUpdateExercise);
    }
}
