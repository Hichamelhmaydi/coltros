package com.example.coltros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ColtrosApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColtrosApplication.class, args);
    }
}