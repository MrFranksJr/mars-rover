package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.api.SimulationStatePresenter;
import io.tripled.marsrover.business.domain.rover.RoverHeading;

import java.util.List;

public class SimulationStateRestPresenter implements SimulationStatePresenter {
    private SimulationState simulationState;

    private RoverViewDTO map(RoverState x) {
        return new RoverViewDTO(x.roverId().id(), mapHeading(x), x.coordinate().xCoordinate(), x.coordinate().yCoordinate());
    }

    private String mapHeading(RoverState x) {
        final RoverHeading roverHeading = x.roverHeading();
        return switch (roverHeading) {
            case NORTH -> "NORTH";
            case EAST -> "EAST";
            case SOUTH -> "SOUTH";
            case WEST -> "WEST";
        };
    }

    @Override
    public void simulationState(SimulationState simulationState) {
        this.simulationState = simulationState;
    }

    public SimulationViewDTO getSimulationState() {
        return mapToViewDTO(simulationState);
    }

    private SimulationViewDTO mapToViewDTO(SimulationState simulationState) {
        final List<RoverViewDTO> objectStream = simulationState.roverList().stream().map(this::map).toList();
        return new SimulationViewDTO(simulationState.simulationSize(), simulationState.totalCoordinates(), objectStream);
    }
}
