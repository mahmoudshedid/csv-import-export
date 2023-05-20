package org.csv.domain.usecases.exercise;

import lombok.RequiredArgsConstructor;
import org.csv.domain.entities.Exercise;
import org.csv.domain.repository.ExerciseRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateExerciseUseCase {

    private final ExerciseRepository exerciseRepository;

    public Exercise execute(Exercise exercise) {
        if (exercise == null) {
            throw new IllegalArgumentException("Exercise cannot be null");
        }

        Exercise exerciseToCreate = exercise.toBuilder()
                .updatedBy(exercise.getCreatedBy())
                .build();

        return this.exerciseRepository.save(exerciseToCreate);
    }
}
