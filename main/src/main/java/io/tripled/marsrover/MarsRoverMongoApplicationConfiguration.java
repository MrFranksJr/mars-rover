package io.tripled.marsrover;

import io.tripled.marsrover.business.api.MarsRoverController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MarsRoverMongoApplicationConfiguration {
    @Bean
    MarsRoverController marsRoverController(SimulationRepository simulationRepository) {
        return new MarsRoverController(simulationRepository);
    }

}
