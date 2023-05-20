package org.csv.data.entites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.csv.domain.entities.Exercise;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Table(name = "exercises")
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ExerciseData extends DataBaseEntity {

    @Column(nullable = false)
    private String source;

    @Column(name = "code_list_code", nullable = false)
    private String codeListCode;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "display_value", nullable = false)
    private String displayValue;

    @Column(name = "long_description")
    @Lob
    private String longDescription;

    @Column(name = "from_date", nullable = false)
    private LocalDateTime fromDate;

    @Column(name = "to_date")
    private LocalDateTime toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;

    public ExerciseData(Exercise exercise) {
        super(exercise);
    }

    public Exercise toEntity() {
        return Exercise.builder()
                .id(this.getId())
                .source(this.source)
                .codeListCode(this.codeListCode)
                .code(this.code)
                .displayValue(this.displayValue)
                .longDescription(this.longDescription)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .sortingPriority(this.sortingPriority)
                .createdBy(this.getCreatedBy())
                .createdAt(this.getCreatedAt())
                .updatedBy(this.getUpdatedBy())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}
