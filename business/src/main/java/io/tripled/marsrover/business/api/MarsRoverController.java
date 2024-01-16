package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;

public class MarsRoverController implements MarsRoverApi{

    private  final SimulationRepository simulationRepository;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    @Override
    public RoverState landRover(int xCoordinate, int yCoordinate) {
        Rover r1 = new Rover("R1");
        r1.setPosition(xCoordinate, yCoordinate);
        simulationRepository.getSimulation().addRover(r1);
        return r1.getState();
    }
}
