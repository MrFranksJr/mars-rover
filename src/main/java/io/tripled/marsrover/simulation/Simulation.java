package io.tripled.marsrover.simulation;

public enum Simulation {
    SIM_WORLD;

    public int simSize = 0;

    public void setSimSize(int validatedMaxCoordinate) {
        this.simSize = calcSimSize(validatedMaxCoordinate);
    }

    private int calcSimSize(int validatedMaxCoordinate) {
        return (int) Math.pow(validatedMaxCoordinate+1,2);
    }
}