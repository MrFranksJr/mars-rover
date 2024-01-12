package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.SimulationRepository;

public class LandCommand implements Command {
    private final int xCoordinate;
    private final int yCoordinate;
    private final SimulationRepository simRepo;

    public LandCommand(int xCoordinate, int yCoordinate, SimulationRepository simRepo) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.simRepo = simRepo;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        if(roverMissesSimulation()) {
            messagePresenter.roverMissesSimulation(xCoordinate, yCoordinate, simRepo);
        } else {
            Rover r1 = new Rover("R1");
            r1.setPosition(xCoordinate, yCoordinate);
            simRepo.getSimulation().addRover(r1);
            messagePresenter.landRoverMessage(xCoordinate, yCoordinate, r1);
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