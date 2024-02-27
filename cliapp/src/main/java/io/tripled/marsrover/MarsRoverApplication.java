package io.tripled.marsrover;

import io.tripled.marsrover.cli.input.InputReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MarsRoverApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("io.tripled.marsrover");
        final InputReader bean = applicationContext.getBean(InputReader.class);
        bean.readInput();
    }
}