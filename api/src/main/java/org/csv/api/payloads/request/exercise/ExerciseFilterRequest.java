package org.csv.api.payloads.request.exercise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.csv.api.constant.Validation;
import org.csv.domain.filter.exercise.ExerciseFilters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExerciseFilterRequest {

    @ApiModelProperty(notes = "Search for Exercise by Source", example = "Zip")
    @Pattern(regexp = Validation.CODE_PATTERN, message = "Enter a valid Source Name")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Source Shouldn't be more than 100")
    private String source;

    @ApiModelProperty(notes = "Search for Exercise by Code List Code", example = "ZIB001")
    @Pattern(regexp = Validation.CODE_PATTERN, message = "Enter a valid Code List Code")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Code List Code Shouldn't be more than 100")
    private String codeListCode;

    @ApiModelProperty(notes = "Search for Exercise by Code", example = "271636001")
    @Pattern(regexp = Validation.CODE_PATTERN, message = "Enter a valid Code")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Code Shouldn't be more than 100")
    private String code;

    @ApiModelProperty(notes = "Search for Exercise by Display Name", example = "Polsslag regelmatig")
    @Pattern(regexp = Validation.NAME_PATTERN, message = "Enter a valid Exercise Name")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Display Name Shouldn't be more than 100")
    private String displayValue;

    @ApiModelProperty(notes = "Search for Exercise by Description", example = "The long description is necessary")
    @Pattern(regexp = Validation.FREE_TEXT_PATTERN, message = "Enter a valid Exercise Description")
    @Size(max = Validation.MAX_LENGTH_FREE_TEXT, message = "Exercise Description Shouldn't be more than 255")
    private String longDescription;

    @ApiModelProperty(notes = "Search for Exercise by Sorting Priority", example = "1")
    @Positive(message = "Sorting Priority should be positive")
    private Long sortingPriority;

    @ApiModelProperty(notes = "Search for Exercise by From Date", example = "MM/dd/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime fromDate;

    @ApiModelProperty(notes = "Search for Exercise by To Date", example = "MM/dd/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime toDate;

    @ApiModelProperty(notes = "Search for Exercise by Created By", example = "example@example.com")
    @Pattern(regexp = Validation.EMAIL_PATTERN, message = "Enter a valid email")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Created By 'email' Shouldn't be more than 100")
    private String createdBy;

    @ApiModelProperty(notes = "Search for Exercise by Updated By", example = "example@example.com")
    @Pattern(regexp = Validation.EMAIL_PATTERN, message = "Enter a valid email")
    @Size(max = Validation.MAX_LENGTH_NAME, message = "Exercise Updated By 'email' Shouldn't be more than 100")
    private String updatedBy;

    public ExerciseFilters toEntity() {
        return ExerciseFilters.builder()
                .source(this.source)
                .codeListCode(this.codeListCode)
                .code(this.code)
                .displayValue(this.displayValue)
                .longDescription(this.longDescription)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .sortingPriority(this.sortingPriority)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }
}
