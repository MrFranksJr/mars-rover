package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;

import java.util.Objects;

public class UnknownCommand implements Command {
    private final String input;

    public UnknownCommand(String input) {
        this.input = input;
    }

    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.unknownCommand(input);
    }

    @Override
    public String toString() {
        return "UnknownCommand{" +
                "input='" + input + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnknownCommand that = (UnknownCommand) o;
        return Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input);
    }
}
