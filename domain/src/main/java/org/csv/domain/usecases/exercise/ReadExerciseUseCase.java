package org.csv.domain.usecases.exercise;

import lombok.RequiredArgsConstructor;
import org.csv.domain.entities.Exercise;
import org.csv.domain.exception.ResourceNotFoundException;
import org.csv.domain.filter.PageFilters;
import org.csv.domain.filter.exercise.ExerciseFilters;
import org.csv.domain.filter.exercise.ExerciseSortFilters;
import org.csv.domain.repository.ExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ReadExerciseUseCase {

    private final ExerciseRepository exerciseRepository;

    public Exercise execute(long id) {
        return exerciseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                MessageFormat.format("Exercise not found by id {0}", id)
        ));
    }

    public Page<Exercise> execute(ExerciseFilters filters, ExerciseSortFilters sortFilters, PageFilters pageFilters) {

        Pageable pageable = PageRequest.of(
                pageFilters.getPage() - 1,
                pageFilters.getSize(),
                Sort.Direction.fromString(sortFilters.getDirection().toString()),
                sortFilters.getFields().toString().toLowerCase(Locale.ROOT)
        );

        return this.exerciseRepository.findAll(pageable, filters.getDisplayValue(), filters.getLongDescription());
    }

    public List<Exercise> execute() {
        return this.exerciseRepository.findAll();
    }
}
