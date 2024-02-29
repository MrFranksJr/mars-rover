package io.tripled.marsrover.dbmodel;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

import java.util.List;

public class ObjectMother {
    public static final RoverId R1 = new RoverId(1);
    public static final RoverId R2 = new RoverId(2);
    public static final RoverId R3 = new RoverId(3);
    public static final RoverId R4 = new RoverId(4);
    public static final RoverId R5 = new RoverId(5);
    public static final RoverId R6 = new RoverId(6);
    public static final RoverState ROVERSTATE_R1 = ObjectMother.buildRover1();
    public static final RoverState ROVERSTATE_R2 = ObjectMother.buildRover2();
    public static final RoverState ROVERSTATE_R3 = ObjectMother.buildRover3();
    public static final RoverState ROVERSTATE_R4 = ObjectMother.buildRover4();
    public static final RoverState ROVERSTATE_R5 = ObjectMother.buildRover5();

    private static RoverState buildRover3() {
        return RoverState.newBuilder()
                .withRoverId("R3")
                .withCoordinate(new Coordinate(1, 3))
                .withRoverHeading(RoverHeading.SOUTH)
                .build();
    }

    private static RoverState buildRover4() {
        return RoverState.newBuilder()
                .withRoverId("R4")
                .withCoordinate(new Coordinate(2, 7))
                .withRoverHeading(RoverHeading.EAST)
                .build();
    }

    private static RoverState buildRover5() {
        return RoverState.newBuilder()
                .withRoverId("R5")
                .withCoordinate(new Coordinate(4, 9))
                .withRoverHeading(RoverHeading.WEST)
                .withHitPoints(0)
                .build();
    }

    public static SimulationSnapshot buildSimpleSimulation() {
        return buildSimulationState(List.of(
                ROVERSTATE_R2, ROVERSTATE_R1
        ));
    }
    public static SimulationSnapshot buildEmptySimulation() {
        return buildSimulationState(List.of());
    }

    public static SimulationSnapshot buildComplexeSimulationState() {
        return buildSimulationState(List.of(
                ROVERSTATE_R4, ROVERSTATE_R2, ROVERSTATE_R1, ROVERSTATE_R3, ROVERSTATE_R5
        ));
    }

    public static RoverState buildRover2() {
        return RoverState.newBuilder()
                .withRoverId("R2")
                .withCoordinate(new Coordinate(5, 0))
                .build();
    }

    public static RoverState buildRover1() {
        return RoverState.newBuilder()
                .withRoverId("R1")
                .withCoordinate(new Coordinate(10, 0))
                .build();
    }

    public static SimulationSnapshot buildSimulationState(List<RoverState> roverList) {
        return SimulationSnapshot.newBuilder()
                .withId(SimulationId.create())
                .withSimSize(10)
                .withTotalCoordinates(121)
                .withRoverList(roverList)
                .build();
    }


}