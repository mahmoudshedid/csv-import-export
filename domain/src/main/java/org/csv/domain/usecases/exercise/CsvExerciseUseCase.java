package org.csv.domain.usecases.exercise;

import lombok.RequiredArgsConstructor;
import org.csv.domain.entities.Exercise;
import org.csv.domain.repository.ExerciseRepository;
import org.csv.domain.utils.CSVHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvExerciseUseCase {

    private final ExerciseRepository exerciseRepository;

    public void executeSave(MultipartFile file) {
        try {
            List<Exercise> exercises = CSVHelper.csvToExercises(file.getInputStream());
            this.exerciseRepository.saveAll(exercises);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream executeLoad() {
        List<Exercise> exercises = this.exerciseRepository.findAll();
        ByteArrayInputStream in = CSVHelper.exercisesToCSV(exercises);
        return in;
    }
}
