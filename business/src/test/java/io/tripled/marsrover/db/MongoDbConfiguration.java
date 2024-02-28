package io.tripled.marsrover.db;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.tripled.marsrover.business.dbmodel.MongoDbSimulationRepository;
import io.tripled.marsrover.business.dbmodel.SimulationDocumentDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoDbConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(uri);
    }

    @Bean
    MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, db);
    }
    @Bean
    MongoDbSimulationRepository getSimulationRepo(SimulationDocumentDao simulationDocumentDao){
        return new MongoDbSimulationRepository(simulationDocumentDao);
    }


}
