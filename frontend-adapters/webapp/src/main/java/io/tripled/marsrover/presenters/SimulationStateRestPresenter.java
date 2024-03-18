package io.tripled.marsrover.presenters;

import rover.RoverState;
import io.tripled.marsrover.simulation.SimulationSnapshot;
import io.tripled.marsrover.SimulationStatePresenter;
import io.tripled.marsrover.dto.RoverViewDTO;
import io.tripled.marsrover.dto.SimulationViewDTO;
import io.tripled.marsrover.vocabulary.Heading;

import java.util.ArrayList;
import java.util.List;

public class SimulationStateRestPresenter implements SimulationStatePresenter {
    private final List<SimulationSnapshot> presentedSimulationSnapshots = new ArrayList<>();
    private SimulationSnapshot simulationSnapshot;

    private RoverViewDTO map(RoverState x) {
        return new RoverViewDTO(x.roverId().id(), mapHeading(x), x.coordinate().xCoordinate(), x.coordinate().yCoordinate(), x.hitpoints(), x.healthState());
    }

    private String mapHeading(RoverState x) {
        final Heading heading = x.heading();
        return switch (heading) {
            case NORTH -> "NORTH";
            case EAST -> "EAST";
            case SOUTH -> "SOUTH";
            case WEST -> "WEST";
        };
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

        for (SimulationSnapshot simulationSnapshot : presentedSimulationSnapshots) {
            final List<RoverViewDTO> objectStream = simulationSnapshot.roverList().stream().map(this::map).toList();
            listToReturn.add(new SimulationViewDTO(simulationSnapshot.id().toString(), simulationSnapshot.simulationSize(), simulationSnapshot.totalCoordinates(), objectStream));
        }
        return listToReturn;
    }

    @Override
    public void simulationState(List<SimulationSnapshot> simulationSnapshots) {
        presentedSimulationSnapshots.addAll(simulationSnapshots);
        if (simulationSnapshots.isEmpty()) {
            this.simulationSnapshot = SimulationSnapshot.NONE;
        } else {
            this.simulationSnapshot = simulationSnapshots.getFirst();
        }
    }

    @Override
    public void simulationState(SimulationSnapshot simulationSnapshot) {
        this.simulationSnapshot = simulationSnapshot;
    }
}
