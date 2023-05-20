package org.csv.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.csv.data.repository")
public class CsvDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsvDataApplication.class, args);
    }
}