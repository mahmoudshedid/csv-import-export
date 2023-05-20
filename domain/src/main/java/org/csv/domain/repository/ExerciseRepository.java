package org.csv.domain.repository;

import org.csv.domain.entities.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository {

    Optional<Exercise> findById(long id);

    Page<Exercise> findAll(Pageable pageable, String name, String description);

    List<Exercise> findAll();

    Exercise save(Exercise exercise);

    void saveAll(List<Exercise> exercises);

    void delete(long id);

    void deleteAll();
}
