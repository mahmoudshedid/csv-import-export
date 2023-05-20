package org.csv.domain.usecases.exercise;

import lombok.RequiredArgsConstructor;
import org.csv.domain.repository.ExerciseRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteExerciseUseCase {

    private final ExerciseRepository exerciseRepository;
    private final ReadExerciseUseCase readExerciseUseCase;

    public void execute(long id) {
        this.readExerciseUseCase.execute(id);

        this.exerciseRepository.delete(id);
    }

    public void executeAll() {
        this.exerciseRepository.deleteAll();
    }
}
