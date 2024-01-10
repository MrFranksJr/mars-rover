package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.simulation.Simulation;

import java.util.Objects;

public class SimSetupCommand implements Command {

    private final String coordinateInput;

    public SimSetupCommand(String coordinateInput) {
        this.coordinateInput = coordinateInput;
    }

    public void execute(MessagePresenter messagePresenter) {
        int maxCoordinate = Integer.parseInt(coordinateInput);
        Simulation simWorld = new Simulation(maxCoordinate);
        messagePresenter.simSetupMessage(simWorld.maxCoordinate, simWorld.simSize);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimSetupCommand that = (SimSetupCommand) o;
        return Objects.equals(coordinateInput, that.coordinateInput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinateInput);
    }
}
