package io.tripled.marsrover;

import io.tripled.marsrover.cli.input.InputReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public class MarsRoverCLIApplication {
    @Autowired
    SimulationRepository simulationRepository;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("io.tripled.marsrover");
        final InputReader bean = applicationContext.getBean(InputReader.class);
        bean.readInput();
    }
}