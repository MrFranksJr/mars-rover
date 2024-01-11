package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.Simulation;
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
    public Simulation execute(MessagePresenter messagePresenter) {
        Rover r1 = new Rover("R1");
        simRepo.getSimulation().addRover(r1);
        messagePresenter.landRoverMessage(xCoordinate, yCoordinate, r1);
        return null;
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