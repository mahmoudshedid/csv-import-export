package org.csv.data.repository;

import org.csv.data.entites.ExerciseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseRepositoryDataJpa extends JpaRepository<ExerciseData, Long> {

    @Query(
            "SELECT exd FROM ExerciseData exd " +
                    "WHERE (LOWER(exd.displayValue) LIKE LOWER(CONCAT('%', :displayValue, '%')) OR :displayValue is null OR :displayValue = '') " +
                    "AND (LOWER(exd.longDescription) LIKE LOWER(CONCAT('%', :longDescription, '%')) OR :longDescription is null OR :longDescription = '')"
    )
    Page<ExerciseData> findAll(@Param("displayValue") String displayValue, @Param("longDescription") String longDescription, Pageable pageable);
}
