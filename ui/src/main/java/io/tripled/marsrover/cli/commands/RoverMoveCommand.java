package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.Objects;

class RoverMoveCommand implements Command {
    private final String commandString;

    public RoverMoveCommand(String commandString) {
        this.commandString = commandString;
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoverMoveCommand that = (RoverMoveCommand) o;
        return Objects.equals(commandString, that.commandString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandString);
    }
}
