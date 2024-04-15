package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.api.rover.RoverState;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = MongoDbConfiguration.class)
class MongoDbSimulationRepositoryTest {

    @Autowired
    private MongoDbSimulationRepository repository;

    @Test
    void simpleSimulationCreation() {
        SimulationSnapshot snapshot = ObjectMother.buildEmptySimulation();
        repository.add(snapshot);
        SimulationId id = snapshot.id();
        System.out.println(id);
        SimulationSnapshot returnedSimulationSnapshot = repository.getSimulation(id).orElseThrow();

        assertEquals(snapshot, returnedSimulationSnapshot);
    }

    @Test
    void simpleSimulationCreationWithSimulationName() {
        SimulationSnapshot snapshot = ObjectMother.buildSimpleSimulationWithSimulationName();
        repository.add(snapshot);
        SimulationId id = snapshot.id();
        String simulationName = snapshot.simulationName();
        System.out.println(id);
        System.out.println(simulationName);
        SimulationSnapshot returnedSimulationSnapshot = repository.getSimulation(id).orElseThrow();

        assertEquals(snapshot, returnedSimulationSnapshot);
        assertEquals(snapshot.simulationName(), simulationName);
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
        List<RoverState> rovers = new ArrayList<>();
        rovers.add(ObjectMother.buildRover1());
        SimulationSnapshot simulationWithRover = SimulationSnapshot.newBuilder()
                .withId(simulationSnapshot.id())
                .withSimSize(10)
                .withRoverList(rovers)
                .build();

        //when
        repository.save(simulationWithRover);

        //then
        SimulationSnapshot landedSimulationSnapshot = repository.getSimulation(id).orElseThrow();
        assertEquals(1, landedSimulationSnapshot.roverList().size());
        Assertions.assertEquals(ObjectMother.R1, landedSimulationSnapshot.roverList().getFirst().roverId());
    }
}