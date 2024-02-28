package io.tripled.marsrover;

import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MarsRoverApplication {
    @Autowired
    SimulationRepository simulationRepository;

    public static void main(String[] args) {
        SpringApplication.run(MarsRoverApplication.class, args);
    }
}