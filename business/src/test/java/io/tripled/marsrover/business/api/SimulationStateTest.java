package io.tripled.marsrover.business.api;

import org.junit.jupiter.api.Test;

import static io.tripled.marsrover.business.ObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationStateTest {


    @Test
    void normalHappyBuild() {
        final SimulationState simulationState = buildSimpleSimulationState();

        assertEquals(10, simulationState.simulationSize());
        assertEquals(1256456, simulationState.totalCoordinates());
        assertThat(simulationState.roverList()).containsExactly(ROVERSTATE_R1, ROVERSTATE_R2);
    }
    @Test
    void complexHappyBuild() {
        final SimulationState simulationState = buildComplexeSimulationState();

        assertEquals(10, simulationState.simulationSize());
        assertEquals(1256456, simulationState.totalCoordinates());
        assertThat(simulationState.roverList()).containsExactly(ROVERSTATE_R1, ROVERSTATE_R2,ROVERSTATE_R3,ROVERSTATE_R4,ROVERSTATE_R5);
    }


}