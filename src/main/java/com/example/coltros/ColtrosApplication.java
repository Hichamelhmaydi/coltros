package com.example.coltros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.example.coltros.Repository")
public class ColtrosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColtrosApplication.class, args);
    }
}