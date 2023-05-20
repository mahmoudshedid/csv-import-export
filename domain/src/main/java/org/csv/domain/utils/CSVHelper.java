package org.csv.domain.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.csv.domain.entities.Exercise;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"source", "codeListCode", "code", "displayValue", "longDescription", "fromDate", "toDate", "sortingPriority"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Exercise> csvToExercises(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Exercise> exercises = new ArrayList<Exercise>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("MM-dd-yyyy")
                    .optionalStart()
                    .appendPattern(" HH:mm")
                    .optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .toFormatter();

            for (CSVRecord csvRecord : csvRecords) {

                Exercise exercise = Exercise.builder()
                        .source(csvRecord.get("source"))
                        .codeListCode(csvRecord.get("codeListCode"))
                        .code(csvRecord.get("code"))
                        .displayValue(csvRecord.get("displayValue"))
                        .longDescription(csvRecord.get("longDescription"))
                        .fromDate(csvRecord.get("fromDate") != null && !csvRecord.get("fromDate").isEmpty() ?
                                LocalDateTime.parse(csvRecord.get("fromDate"), formatter) : null)
                        .toDate(csvRecord.get("toDate") != null && !csvRecord.get("toDate").isEmpty() ?
                                LocalDateTime.parse(csvRecord.get("toDate"), formatter) : null)
                        .sortingPriority(csvRecord.get("sortingPriority") != null && !csvRecord.get("sortingPriority").isEmpty() ?
                                Integer.parseInt(csvRecord.get("sortingPriority")) : null)
                        .build();

                exercises.add(exercise);
            }

            return exercises;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream exercisesToCSV(List<Exercise> exercises) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Exercise exercise : exercises) {
                List<String> data = Arrays.asList(
                        String.valueOf(exercise.getId()),
                        exercise.getSource(),
                        exercise.getCodeListCode(),
                        exercise.getCode(),
                        exercise.getDisplayValue(),
                        exercise.getLongDescription(),
                        exercise.getFromDate() != null ? exercise.getFromDate().toString() : "",
                        exercise.getToDate() != null ? exercise.getToDate().toString() : "",
                        exercise.getSortingPriority() != null ? exercise.getSortingPriority().toString() : ""
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}
