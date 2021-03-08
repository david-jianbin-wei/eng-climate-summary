package com.david.engclimatesummary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication
public class EngClimateSummaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngClimateSummaryApplication.class, args);
    }

}
