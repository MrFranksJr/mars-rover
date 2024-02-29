package io.tripled.marsrover.business.dbmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableAutoConfiguration
//@ContextConfiguration(classes = MongoDbConfiguration.class)
public class EmbeddedMongoTest {
    @Autowired
    private MongoTemplate mongoTemplate;

//    @Test
//    void saveOwnCreatedObjectInDB() {
//        // given
//        final Simulation simulation = new Simulation(10);
//        final SimulationId id = simulation.takeSnapshot().id();
//        SimulationDocument simulationDocument = new SimulationDocument(simulation);
//
//        // when
//        mongoTemplate.save(simulationDocument, "marscollection");
//
//        System.out.println(mongoTemplate.findAll(DBObject.class, "marscollection"));
//
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "marscollection"))
//                .extracting("_id")
//                .contains(id.toString());
//    }
//
//    @Test
//    void extractSimSize() {
//        // given
//        final Simulation simulation = new Simulation(10);
//        SimulationDocument simulationDocument = new SimulationDocument(simulation);
//
//        // when
//        mongoTemplate.save(simulationDocument, "marscollection");
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "marscollection"))
//                .extracting("simulationSize")
//                .contains(10);
//    }

}