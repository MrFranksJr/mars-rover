package io.tripled.marsrover.simulation;

public class Simulation {

    public int simSize;
    public int maxCoordinate;
    public Simulation(int maxCoordinate) {
        this.maxCoordinate = maxCoordinate;
        this.simSize = calcSimSize(maxCoordinate);
    }

    private int calcSimSize(int validatedMaxCoordinate) {
        return (int) Math.pow(validatedMaxCoordinate+1,2);
    }
}