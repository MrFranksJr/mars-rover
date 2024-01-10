package io.tripled.marsrover.simulation;

public class Simulation {
    private int simSize;
    private int maxCoordinate;


    public Simulation(int maxCoordinate) {
        this.maxCoordinate = maxCoordinate;
        this.simSize = calcSimSize(maxCoordinate);
    }

    private int calcSimSize(int validatedMaxCoordinate) {
        return (int) Math.pow(validatedMaxCoordinate+1,2);
    }

    public int getSimSize() {
        return simSize;
    }

    public int getMaxCoordinate() {
        return maxCoordinate;
    }
}