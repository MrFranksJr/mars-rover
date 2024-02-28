package io.tripled.marsrover.business.dbmodel;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.db.MongoDbConfiguration;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.bson.types.ObjectId;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = MongoDbConfiguration.class)
public class EmbeddedMongoTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void saveOwnCreatedObjectInDB() {
        // given
        final Simulation simulation = new Simulation(10);
        final SimulationId id = simulation.takeSnapshot().id();
        SimulationDocument simulationDocument = new SimulationDocument(simulation);

        // when
        mongoTemplate.save(simulationDocument, "marscollection");

        System.out.println(mongoTemplate.findAll(DBObject.class, "marscollection"));

        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "marscollection"))
                .extracting("_id")
                .contains(id.toString());
    }

    @Test
    void extractSimSize() {
        // given
        final Simulation simulation = new Simulation(10);
        SimulationDocument simulationDocument = new SimulationDocument(simulation);

        // when
        mongoTemplate.save(simulationDocument, "marscollection");
        // then
        assertThat(mongoTemplate.findAll(DBObject.class, "marscollection"))
                .extracting("simulationSize")
                .contains(10);
    }

//
//    @Test
//    void addLandedRover() {
//        // given
//        final Simulation simulation = new Simulation(10);
//        simulation.landRover(new Coordinate(5, 5), new Simulation.SimulationLandingEventPublisher() {
//            @Override
//            public void publish(Simulation.SimulationLandEvent event) {
//
//            }
//        });
//        simulation.landRover(new Coordinate(2, 2), new Simulation.SimulationLandingEventPublisher() {
//            @Override
//            public void publish(Simulation.SimulationLandEvent event) {
//
//            }
//        });
//        SimulationDocument simulationDocument = new SimulationDocument(simulation);
//
//        // when
//        mongoTemplate.save(simulationDocument, "marscollection");
//
//        List<DBObject> dbObjects = mongoTemplate.findAll(DBObject.class, "marscollection");
//        assertThat(dbObjects)
//                .extracting("roverList")
//                .asList()
//                .first()
//                .extracting("healthState")
//                .isEqualTo("OPERATIONAL");                ;
//
//
//    }
}