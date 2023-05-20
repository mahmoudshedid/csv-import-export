package org.csv.api.payloads.request.exercise;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csv.api.constant.Validation;
import org.csv.domain.entities.Exercise;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExerciseUpdateRequest {

    @ApiModelProperty(notes = "Exercise ID", example = "1")
    @NotNull(message = "Exercise ID can't be blank")
    @Positive(message = "Exercise ID should be positive")
    private long id;

    @ApiModelProperty(notes = "Exercise Source", example = "Zip")
    @Pattern(regexp = Validation.CODE_PATTERN, message = "Enter a valid Source Name")
    @NotBlank(message = "Exercise Source can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Source Shouldn't be more than 100")
    private String source;

    @ApiModelProperty(notes = "Exercise Code List Code", example = "ZIB001")
    @Pattern(regexp = Validation.CODE_PATTERN, message = "Enter a valid Code List Code")
    @NotBlank(message = "Exercise Code List Code can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Code List Code Shouldn't be more than 100")
    private String codeListCode;

    @ApiModelProperty(notes = "Exercise Code", example = "271636001")
    @Pattern(regexp = Validation.CODE_PATTERN, message = "Enter a valid Code")
    @NotBlank(message = "Exercise Code can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Code Shouldn't be more than 100")
    private String code;

    @ApiModelProperty(notes = "Exercise Display Name", example = "Polsslag regelmatig")
    @Pattern(regexp = Validation.NAME_PATTERN, message = "Enter a valid Exercise Name")
    @NotBlank(message = "Exercise Display Name can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Display Name Shouldn't be more than 100")
    private String displayValue;

    @ApiModelProperty(notes = "Exercise Description", example = "The long description is necessary")
    @Pattern(regexp = Validation.FREE_TEXT_PATTERN, message = "Enter a valid Exercise Description")
    @Size(max = Validation.MAX_LENGTH_FREE_TEXT, message = "Exercise Description Shouldn't be more than 255")
    private String longDescription;

    @ApiModelProperty(notes = "Exercise Sorting Priority", example = "1")
    @Positive(message = "Sorting Priority should be positive")
    @NotNull(message = "Sorting Priority can't be blank")
    private Long sortingPriority;

    @ApiModelProperty(notes = "Exercise From Date", example = "MM/dd/yyyy")
    @NotBlank(message = "From Date can't be blank")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime fromDate;

    @ApiModelProperty(notes = "Exercise To Date", example = "MM/dd/yyyy")
    @NotBlank(message = "To Date can't be blank")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime toDate;

    @ApiModelProperty(notes = "Exercise Updated By", example = "example@example.com")
    @Pattern(regexp = Validation.EMAIL_PATTERN, message = "Enter a valid email")
    @NotBlank(message = "Exercise Updated By Can't be blank")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Updated By 'email' Shouldn't be more than 100")
    private String updatedBy;

    public Exercise toEntity() {
        return Exercise.builder()
                .id(this.id)
                .source(this.source)
                .codeListCode(this.codeListCode)
                .code(this.code)
                .displayValue(this.displayValue)
                .longDescription(this.longDescription)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .sortingPriority(this.sortingPriority)
                .updatedBy(this.updatedBy)
                .build();
    }
}
