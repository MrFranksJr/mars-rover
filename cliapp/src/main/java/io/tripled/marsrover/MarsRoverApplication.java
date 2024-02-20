package io.tripled.marsrover;

import io.tripled.marsrover.cli.input.InputReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MarsRoverApplication {

    public static void main(String[] args) {
//        SpringApplication.run(MarsRoverApplication.class, args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("io.tripled.marsrover");
        final InputReader bean = applicationContext.getBean(InputReader.class);
        bean.readInput();
    }
}