package io.tripled.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Profile("REACT")
@PropertySource("classpath:react-dev.properties")
public class MarsRoverReactApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarsRoverReactApplication.class, args);
    }
}