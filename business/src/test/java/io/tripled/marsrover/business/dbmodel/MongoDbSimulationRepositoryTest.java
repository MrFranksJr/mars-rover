package io.tripled.marsrover.business.dbmodel;

import io.tripled.marsrover.business.ObjectMother;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.db.MongoDbConfiguration;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = MongoDbConfiguration.class)
class MongoDbSimulationRepositoryTest {

    @Autowired
    private MongoDbSimulationRepository repository;

    @Test
    void simpleSimulationCreation() {
        Simulation simulation = new Simulation(10);
        repository.add(simulation);
        SimulationId id = simulation.takeSnapshot().id();

        Simulation returnedSimulation = repository.getSimulation(id).orElseThrow();

        assertEquals(simulation.takeSnapshot(), returnedSimulation.takeSnapshot());
    }

    @Test
    void persistSimulationWithRovers() {
        //given
        SimulationSnapshot simulationSnapshot = ObjectMother.buildComplexeSimulationState();

        //when
        repository.add(Simulation.of(simulationSnapshot));

        //then
        SimulationId id = simulationSnapshot.id();
        Simulation returnedSimulation = repository.getSimulation(id).orElseThrow();
        assertEquals(simulationSnapshot, returnedSimulation.takeSnapshot());
    }

    @Test
    void persistSimulationWithLandedRoversSave() {
        //given
        SimulationSnapshot simulationSnapshot = ObjectMother.buildEmptySimulation();
        repository.add(Simulation.of(simulationSnapshot));
        SimulationId id = simulationSnapshot.id();
        Simulation returnedSimulation = repository.getSimulation(id).orElseThrow();
        returnedSimulation.landRover(new Coordinate(4, 5), event -> {
        });

        //when
        repository.save(returnedSimulation);

        //then
        Simulation landedSimulation = repository.getSimulation(id).orElseThrow();
        assertEquals(1, landedSimulation.takeSnapshot().roverList().size());
        assertEquals(ObjectMother.R1, landedSimulation.takeSnapshot().roverList().getFirst().roverId());
    }
}