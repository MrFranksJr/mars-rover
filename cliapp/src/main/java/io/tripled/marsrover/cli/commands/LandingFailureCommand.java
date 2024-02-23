package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LandingFailureCommand implements Command {
    private final String input;
    private final String coordinateString;

    public LandingFailureCommand(String input) {
        this.input = input;
        this.coordinateString = input.substring(4).trim();
    }

    @Override
    public void execute(MessagePresenter messagePresenter) {
        LandingErrorTypes landingError = getErrorData();
        messagePresenter.landingFailureCommand(coordinateString, landingError);
    }

    LandingErrorTypes getErrorData() {
        String patternString = "(?i)^Land\\s+(\\S+)\\s+(\\S+)$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String[] parts = input.split(" ");
            try {
                int firstCoordinate = Integer.parseInt(parts[1]);
                int secondCoordinate = Integer.parseInt(parts[2]);

                if (firstCoordinate < 0 || secondCoordinate < 0) return LandingErrorTypes.NEGATIVE_INTS;
            } catch (NumberFormatException e) {
                return LandingErrorTypes.RECEIVED_LETTERS;
            }
        } else return LandingErrorTypes.UNABLE_TO_PARSE;
        return LandingErrorTypes.UNABLE_TO_PARSE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LandingFailureCommand that = (LandingFailureCommand) o;

        return input.equals(that.input);
    }

    @Override
    public int hashCode() {
        return input.hashCode();
    }
}
