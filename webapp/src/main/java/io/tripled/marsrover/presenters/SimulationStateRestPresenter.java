package io.tripled.marsrover.presenters;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.api.SimulationStatePresenter;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.dto.RoverViewDTO;
import io.tripled.marsrover.dto.SimulationViewDTO;

import java.util.ArrayList;
import java.util.List;

public class SimulationStateRestPresenter implements SimulationStatePresenter {
    private SimulationSnapshot simulationSnapshot;
    private final List<SimulationSnapshot> simulationSnapshots = new ArrayList<>();

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
    public void simulationState(List<Simulation> simulations) {
        for (Simulation simulation : simulations) {
            simulationSnapshots.add(simulation.takeSnapshot());
        }
        if (simulationSnapshots.isEmpty()) {
            this.simulationSnapshot = SimulationSnapshot.NONE;
        } else {
            this.simulationSnapshot = simulationSnapshots.getFirst();
        }
    }

    @Override
    public void simulationState(Simulation simulation) {
        this.simulationSnapshot = simulation.takeSnapshot();
    }

    public SimulationViewDTO getSimulationState() {
        return mapToSimulationViewDTO();
    }

    public List<SimulationViewDTO> getSimulationStates() {
        return mapToSimulationViewDTOs();
    }

    private SimulationViewDTO mapToSimulationViewDTO() {
        final List<RoverViewDTO> objectStream = simulationSnapshot.roverList().stream().map(this::map).toList();
        return new SimulationViewDTO(simulationSnapshot.id().toString(), simulationSnapshot.simulationSize(), simulationSnapshot.totalCoordinates(), objectStream);
    }

    private List<SimulationViewDTO> mapToSimulationViewDTOs() {
        List<SimulationViewDTO> listToReturn = new ArrayList<>();

        for (SimulationSnapshot simulationSnapshot : simulationSnapshots) {
            final List<RoverViewDTO> objectStream = simulationSnapshot.roverList().stream().map(this::map).toList();
            listToReturn.add(new SimulationViewDTO(simulationSnapshot.id().toString(), simulationSnapshot.simulationSize(), simulationSnapshot.totalCoordinates(), objectStream));
        }
        return listToReturn;
    }
}
