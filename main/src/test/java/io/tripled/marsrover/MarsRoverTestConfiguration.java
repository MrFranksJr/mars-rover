package io.tripled.marsrover;

import io.tripled.marsrover.inmemory.InMemSimulationRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MarsRoverTestConfiguration {
    @Bean
    @Profile("INMEM")
    InMemSimulationRepo inMemSimulationRepository() {
        return new InMemSimulationRepo();
    }
}
