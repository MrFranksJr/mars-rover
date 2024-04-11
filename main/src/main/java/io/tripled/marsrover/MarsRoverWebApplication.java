package io.tripled.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Profile("WEB")
@PropertySource("classpath:webapp-dev.properties")
public class MarsRoverWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsRoverWebApplication.class, args);
    }
}