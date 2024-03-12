package io.tripled.marsrover;

import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarsRoverApplicationConfiguration {
    @Bean
    MarsRoverController marsRoverController(SimulationRepository simulationRepository) {
        return new MarsRoverController(simulationRepository);
    }
}
