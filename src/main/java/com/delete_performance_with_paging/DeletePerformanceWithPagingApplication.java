package com.delete_performance_with_paging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DeletePerformanceWithPagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeletePerformanceWithPagingApplication.class, args);
    }

}
