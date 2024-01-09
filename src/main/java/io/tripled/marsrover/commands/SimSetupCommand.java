package io.tripled.marsrover.commands;

import io.tripled.marsrover.MessagePresenter;

import java.util.Objects;

public class SimSetupCommand implements Command {

    private final String maxCoordinate;

    public SimSetupCommand(String maxCoordinate) {
        this.maxCoordinate = maxCoordinate;
    }

    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.simSetupCommand(maxCoordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimSetupCommand that = (SimSetupCommand) o;
        return Objects.equals(maxCoordinate, that.maxCoordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxCoordinate);
    }
}
