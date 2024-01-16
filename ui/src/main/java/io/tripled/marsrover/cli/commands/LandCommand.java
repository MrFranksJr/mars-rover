package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.messages.MessagePresenter;

public class LandCommand implements Command {
    private final int xCoordinate;
    private final int yCoordinate;
    private final SimulationRepository simRepo;
    private final MarsRoverApi marsRoverApi;

    public LandCommand(int xCoordinate, int yCoordinate, SimulationRepository simRepo, MarsRoverApi marsRoverApi) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.simRepo = simRepo;
        this.marsRoverApi = marsRoverApi;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        if (roverMissesSimulation()) {
            messagePresenter.roverMissesSimulation(xCoordinate, yCoordinate, simRepo);
        } else {
            final RoverState r1 = marsRoverApi.landRover(xCoordinate, yCoordinate);
            messagePresenter.landRoverMessage(r1);
        }
    }

    private boolean roverMissesSimulation() {
        return simRepo.getSimulation().getSimulationSize() < xCoordinate || simRepo.getSimulation().getSimulationSize() < yCoordinate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LandCommand that = (LandCommand) o;

        if (xCoordinate != that.xCoordinate) return false;
        return yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }
}