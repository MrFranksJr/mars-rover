package io.tripled.marsrover.validators;

import io.tripled.marsrover.commands.SimSetupCommand;
import io.tripled.marsrover.simulation.Simulation;

public class LandCommandValidator {
    String commandInput;
    public LandCommandValidator(String commandInput) {
        this.commandInput = commandInput;
    }

    public boolean isValid(String commandInput) {
        if (containsLand() && hasValidStructure() && isWithingBounds()) {
            return true;
        }
        return false;
    }

    private boolean isWithingBounds() {
        return true;
    }

    private boolean hasValidStructure() {
        return true;
    }

    private boolean containsLand() {
        return true;
    }
}