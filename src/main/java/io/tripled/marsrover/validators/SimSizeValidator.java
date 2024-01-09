package io.tripled.marsrover.validators;

public class SimSizeValidator {

    public static boolean validateMaxCoordinate(String maxCoordinate) {
        return containsOnlyNumbers(maxCoordinate) && isWithinLimit(maxCoordinate);
    }

    private static boolean containsOnlyNumbers(String coordinate) {
        return coordinate.matches("\\d+");
    }

    private static boolean isWithinLimit(String coordinate) {
        int maxCoordinate = Integer.parseInt(coordinate);
        return maxCoordinate >= 0 && maxCoordinate <= 100;
    }
}
