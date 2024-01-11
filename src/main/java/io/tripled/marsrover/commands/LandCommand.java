package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.Simulation;
import io.tripled.marsrover.simulation.SimulationRepository;

import java.util.Objects;

public class LandCommand implements Command {
    private int landingPosX = 0;
    private int landingPosY = 0;
    private SimulationRepository simRepo = null;

    public LandCommand(int xCoordinate, int yCoordinate) {

    }

    @Override
    public Simulation execute(MessagePresenter messagePresenter) {
        Rover r1 = new Rover("R1");
        simRepo.getSimulation().addRover(r1);
        messagePresenter.landRoverMessage(landingPosX, landingPosY, r1.getRoverName());
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LandCommand that = (LandCommand) o;

        if (landingPosX != that.landingPosX) return false;
        if (landingPosY != that.landingPosY) return false;
        return Objects.equals(simRepo, that.simRepo);
    }

    @Override
    public int hashCode() {
        int result = landingPosX;
        result = 31 * result + landingPosY;
        result = 31 * result + (simRepo != null ? simRepo.hashCode() : 0);
        return result;
    }
}