package io.tripled.marsrover;

import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.dbmodel.MongoDbSimulationRepository;
import io.tripled.marsrover.dbmodel.SimulationDocumentDao;
import io.tripled.marsrover.inmemory.InMemSimulationRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MarsRoverMongoApplicationConfiguration {
    @Bean
    MarsRoverController marsRoverController(SimulationRepository simulationRepository) {
        return new MarsRoverController(simulationRepository);
    }

    @Bean
    @Profile("INMEM")
    SimulationRepository inMemSimulationRepository() {
        return new InMemSimulationRepo();
    }

    @Bean
    @Profile("MONGO")
    SimulationRepository mongoRepo(SimulationDocumentDao documentDao) {
        return new MongoDbSimulationRepository(documentDao);
    }
}
