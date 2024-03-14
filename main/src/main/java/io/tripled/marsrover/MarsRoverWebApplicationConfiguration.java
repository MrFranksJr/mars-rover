package io.tripled.marsrover;

import io.tripled.marsrover.business.api.MarsRoverController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarsRoverWebApplicationConfiguration {
    @Bean
    MarsRoverController marsRoverController(SimulationRepository simulationRepository) {
        return new MarsRoverController(simulationRepository);
    }
}
