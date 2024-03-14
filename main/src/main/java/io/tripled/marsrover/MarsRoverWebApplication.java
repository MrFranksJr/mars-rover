package io.tripled.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class MarsRoverWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsRoverWebApplication.class, args);
    }
}