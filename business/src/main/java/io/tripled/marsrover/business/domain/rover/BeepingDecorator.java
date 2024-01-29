package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.api.RoverMovePresenterChange;

public class BeepingDecorator implements Vehicle {
    private final Vehicle vehicle;
    private final RoverMovePresenterChange presenter;
    private BeepingDecorator(Vehicle vehicle, RoverMovePresenterChange presenter) {
        this.vehicle = vehicle;
        this.presenter = presenter;
    }

    public static Vehicle decorate(Vehicle vehicle, RoverMovePresenterChange presenter) {
        return new BeepingDecorator(vehicle, presenter);
    }

    @Override
    public void moveForward() {
        System.out.println(" Moving Forward! BEEP BEEP!");
        vehicle.moveForward();
    }

    @Override
    public void moveBackward() {
        System.out.println("Moving Backwards! BEEP BEEP!");
        vehicle.moveBackward();
    }

    @Override
    public void turnLeft() {
        System.out.println("Turning Left! BEEP BEEP!");
        vehicle.turnLeft();
    }

    @Override
    public void turnRight() {
        System.out.println("Turning Right! BEEP BEEP!");
        vehicle.turnRight();
    }
}
