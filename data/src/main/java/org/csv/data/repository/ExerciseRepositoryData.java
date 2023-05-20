package org.csv.data.repository;

import lombok.RequiredArgsConstructor;
import org.csv.data.entites.ExerciseData;
import org.csv.domain.entities.Exercise;
import org.csv.domain.repository.ExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Repository
@Transactional
@RequiredArgsConstructor
public class ExerciseRepositoryData implements ExerciseRepository {

    private final ExerciseRepositoryDataJpa exerciseRepositoryDataJpa;

    @Override
    @Transactional(readOnly = true)
    public Optional<Exercise> findById(long id) {
        return this.exerciseRepositoryDataJpa.findById(id).map(ExerciseData::toEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercise> findAll(Pageable pageable, String name, String description) {
        return this.exerciseRepositoryDataJpa.findAll(name, description, pageable).map(ExerciseData::toEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> findAll() {
        return this.exerciseRepositoryDataJpa.findAll().stream().map(ExerciseData::toEntity).collect(Collectors.toList());
    }

    @Override
    public Exercise save(Exercise exercise) {
        return this.exerciseRepositoryDataJpa.save(ExerciseData.builder()
                .id(exercise.getId())
                .source(exercise.getSource())
                .codeListCode(exercise.getCodeListCode())
                .code(exercise.getCode())
                .displayValue(exercise.getDisplayValue())
                .longDescription(exercise.getLongDescription())
                .fromDate(exercise.getFromDate())
                .toDate(exercise.getToDate())
                .sortingPriority(exercise.getSortingPriority())
                .createdBy(exercise.getCreatedBy())
                .createdAt(exercise.getCreatedAt())
                .updatedBy(exercise.getUpdatedBy())
                .updatedAt(exercise.getUpdatedAt())
                .build()).toEntity();
    }

    @Override
    public void saveAll(List<Exercise> exercises) {
        exercises.forEach(this::save);
    }

    @Override
    public void delete(long id) {
        this.exerciseRepositoryDataJpa.deleteById(id);
    }

    @Override
    public void deleteAll() {
        this.exerciseRepositoryDataJpa.deleteAll();
    }
}
