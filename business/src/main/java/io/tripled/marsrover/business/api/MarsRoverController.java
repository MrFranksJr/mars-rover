package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.simulation.SimulationRepository;

public class MarsRoverController implements MarsRoverApi {

    private final SimulationRepository simulationRepository;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    @Override
    public RoverState landRover(int xCoordinate, int yCoordinate) {
        simulationRepository.getSimulation().landRover(xCoordinate, yCoordinate);
        return returnRoverState();
    }

    private RoverState returnRoverState() {
        return simulationRepository.getSimulation().getRoverList().getFirst().getState();
    }
}
