package io.tripled.marsrover.business.api;

import io.tripled.marsrover.simulation.SimulationSnapshot;
import org.junit.jupiter.api.Test;

import static io.tripled.marsrover.business.ObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationSnapshotTest {


    @Test
    void normalHappyBuild() {
        final SimulationSnapshot simulationSnapshot = buildSimpleSimulation();

        assertEquals(10, simulationSnapshot.simulationSize());
        assertEquals(121, simulationSnapshot.totalCoordinates());
        assertThat(simulationSnapshot.roverList()).containsExactly(ROVERSTATE_R1, ROVERSTATE_R2);
    }
    @Test
    void complexHappyBuild() {
        final SimulationSnapshot simulationSnapshot = buildComplexeSimulationState();

        assertEquals(10, simulationSnapshot.simulationSize());
        assertEquals(121, simulationSnapshot.totalCoordinates());
        assertThat(simulationSnapshot.roverList()).containsExactly(ROVERSTATE_R1, ROVERSTATE_R2,ROVERSTATE_R3,ROVERSTATE_R4,ROVERSTATE_R5);
    }


}