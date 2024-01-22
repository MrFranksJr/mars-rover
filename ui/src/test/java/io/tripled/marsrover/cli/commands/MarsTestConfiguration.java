package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MarsTestConfiguration {

    @Bean
    MarsRoverController getMarsRoverController() {
        return new MarsRoverController(simulationRepository());
    }

    @Bean
    SimulationRepository simulationRepository() {
        return new InMemSimulationRepo();
    }

}
