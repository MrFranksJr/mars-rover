package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = MongoDbConfiguration.class)
class MongoDbSimulationRepositoryTest {

    @Autowired
    private MongoDbSimulationRepository repository;

    @Test
    void simpleSimulationCreation() {
        SimulationSnapshot snapshot = new Simulation(10).takeSnapshot();
        repository.add(snapshot);
        SimulationId id = snapshot.id();

        SimulationSnapshot returnedSimulationSnapshot = repository.getSimulation(id).orElseThrow();

        assertEquals(snapshot, returnedSimulationSnapshot);
    }

    @Test
    void persistSimulationWithRovers() {
        //given
        SimulationSnapshot simulationSnapshot = ObjectMother.buildComplexeSimulationState();

        //when
        repository.add(simulationSnapshot);

        //then
        SimulationId id = simulationSnapshot.id();
        SimulationSnapshot returnedSimulationSnapshot = repository.getSimulation(id).orElseThrow();
        assertEquals(simulationSnapshot, returnedSimulationSnapshot);
    }

    @Test
    void persistSimulationWithLandedRoversSave() {
        //given
        SimulationSnapshot simulationSnapshot = ObjectMother.buildEmptySimulation();
        SimulationId id = simulationSnapshot.id();
        repository.add(simulationSnapshot);
        Simulation simulation = Simulation.of(simulationSnapshot);
        simulation.landRover(new Coordinate(4,5),event -> {});

        //when
        repository.save(simulation.takeSnapshot());

        //then
        SimulationSnapshot landedSimulationSnapshot = repository.getSimulation(id).orElseThrow();
        assertEquals(1, landedSimulationSnapshot.roverList().size());
        Assertions.assertEquals(ObjectMother.R1, landedSimulationSnapshot.roverList().getFirst().roverId());
    }
}