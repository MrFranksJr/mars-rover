package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.api.SimulationStatePresenter;
import io.tripled.marsrover.business.domain.rover.RoverHeading;

import java.util.List;

public class SimulationStateRestPresenter implements SimulationStatePresenter {
    private SimulationSnapshot simulationSnapshot;

    private RoverViewDTO map(RoverState x) {
        return new RoverViewDTO(x.roverId().id(), mapHeading(x), x.coordinate().xCoordinate(), x.coordinate().yCoordinate(), x.hitpoints(), x.healthState());
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
    public void simulationState(SimulationSnapshot simulationSnapshot) {
        this.simulationSnapshot = simulationSnapshot;
    }

    public SimulationViewDTO getSimulationState() {
        return mapToViewDTO(simulationSnapshot);
    }

    private SimulationViewDTO mapToViewDTO(SimulationSnapshot simulationSnapshot) {
        final List<RoverViewDTO> objectStream = simulationSnapshot.roverList().stream().map(this::map).toList();
        return new SimulationViewDTO(simulationSnapshot.simulationSize(), simulationSnapshot.totalCoordinates(), objectStream);
    }
}
