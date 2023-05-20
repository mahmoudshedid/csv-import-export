package org.csv.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csv.api.payloads.ApiResponsePayload;
import org.csv.api.payloads.request.PageFiltersRequest;
import org.csv.api.payloads.request.exercise.ExerciseCreateRequest;
import org.csv.api.payloads.request.exercise.ExerciseFilterRequest;
import org.csv.api.payloads.request.exercise.ExerciseSortFilterRequest;
import org.csv.api.payloads.request.exercise.ExerciseUpdateRequest;
import org.csv.api.payloads.response.exercise.ExerciseResponse;
import org.csv.api.payloads.response.exercise.ResponseMessage;
import org.csv.domain.entities.Exercise;
import org.csv.domain.usecases.exercise.CreateExerciseUseCase;
import org.csv.domain.usecases.exercise.CsvExerciseUseCase;
import org.csv.domain.usecases.exercise.DeleteExerciseUseCase;
import org.csv.domain.usecases.exercise.ReadExerciseUseCase;
import org.csv.domain.usecases.exercise.UpdateExerciseUseCase;
import org.csv.domain.utils.CSVHelper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "ExerciseRestController", tags = "list exercises, create exercises, update exercises, delete exercises")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/exercise")
public class ExerciseRestController {

    private final ReadExerciseUseCase readExerciseUseCase;
    private final CreateExerciseUseCase createExerciseUseCase;
    private final UpdateExerciseUseCase updateExerciseUseCase;
    private final DeleteExerciseUseCase deleteExerciseUseCase;
    private final CsvExerciseUseCase csvExerciseUseCase;

    @ApiOperation(value = "Get exercise by existing ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful request"),
            @ApiResponse(code = 404, message = "Exercise not found by the given ID")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ApiResponsePayload<ExerciseResponse> getExistExercise(
            @ApiParam(value = "Exercise ID", required = true)
            @NotNull(message = "Exercise ID is required")
            @PathVariable(name = "id") long id
    ) {
        log.info("Get exercise by existing ID: {}", id);

        Exercise existExercise = this.readExerciseUseCase.execute(id);

        return ApiResponsePayload.<ExerciseResponse>builder()
                .data(new ExerciseResponse(existExercise))
                .build();
    }

    @ApiOperation(value = "Fetch all Exercises by filters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successful request")})
    @RequestMapping(method = RequestMethod.GET, path = "/fetch/all")
    public ApiResponsePayload<List<ExerciseResponse>> fetchAllPageable(
            @Valid ExerciseFilterRequest exerciseFilterRequest,
            @Valid ExerciseSortFilterRequest exerciseSortFilterRequest,
            @Valid PageFiltersRequest pageFiltersRequest
    ) {
        log.info("Fetching all exercises filtered by {}", exerciseFilterRequest);

        Page<Exercise> exercises = this.readExerciseUseCase.execute(
                exerciseFilterRequest.toEntity(),
                exerciseSortFilterRequest.toEntity(),
                pageFiltersRequest.toEntity()
        );

        return ApiResponsePayload.<List<ExerciseResponse>>builder()
                .pageable(exercises.getPageable())
                .data(exercises.getContent().stream().map(ExerciseResponse::new).collect(Collectors.toList()))
                .build();
    }

    @ApiOperation(value = "Create a new Exercise")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exercise created Successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponsePayload<ExerciseResponse> createNewExercise(
            @ApiParam(value = "Exercise Properties", required = true)
            @Valid @RequestBody ExerciseCreateRequest exerciseCreateRequest
    ) {
        log.info("Create a new Exercise by Properties {}", exerciseCreateRequest);

        Exercise newExercise = this.createExerciseUseCase.execute(exerciseCreateRequest.toEntity());

        return ApiResponsePayload.<ExerciseResponse>builder()
                .data(new ExerciseResponse(newExercise))
                .build();
    }

    @ApiOperation(value = "Upload a new Exercise by CSV")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exercise created Successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                this.csvExerciseUseCase.executeSave(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @ApiOperation(value = "Download exist Exercise as CSV")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exercise Download Successfully"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "exercise.csv";
        InputStreamResource file = new InputStreamResource(this.csvExerciseUseCase.executeLoad());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @ApiOperation(value = "Update exist Exercise By ID")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exercise Update"),
            @ApiResponse(code = 400, message = "Bad input")
    })
    @RequestMapping(method = RequestMethod.PATCH)
    public ApiResponsePayload<ExerciseResponse> updateExistExercise(
            @ApiParam(value = "Properties of the Exercise", required = true)
            @Valid @RequestBody ExerciseUpdateRequest exerciseUpdateRequest
    ) {
        log.info("Updating the Exercise by Properties {}", exerciseUpdateRequest);

        Exercise updatedExercise = this.updateExerciseUseCase.execute(exerciseUpdateRequest.toEntity());

        return ApiResponsePayload.<ExerciseResponse>builder()
                .data(new ExerciseResponse(updatedExercise))
                .build();
    }

    @ApiOperation(value = "Delete Exist exercise by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Exercise not found by the given ID")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteExistExercise(
            @ApiParam(value = "exercise ID", required = true)
            @NotNull(message = "Exercise ID is required")
            @PathVariable(name = "id") long id
    ) {
        log.info("Deleting the exercise by its id. Id: {}", id);

        this.deleteExerciseUseCase.execute(id);
    }

    @ApiOperation(value = "Delete all exercises")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public void deleteAll() {
        log.info("Deleting all exercises");

        this.deleteExerciseUseCase.executeAll();
    }
}
