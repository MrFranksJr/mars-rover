package io.tripled.marsrover.business.domain.rover;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggingDecorator implements Vehicle {
    private final Vehicle vehicle;
    private final Logger logger = LogManager.getLogger(LoggingDecorator.class);

    private LoggingDecorator(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public static Vehicle decorate(Vehicle vehicle) {
        return new LoggingDecorator(vehicle);
    }

    @Override
    public void moveForward() {
        logger.info("Moving Forward! BEEPBEEP!");
        vehicle.moveForward();
    }

    @Override
    public void moveBackward() {
        logger.info("Moving Backward! BEEPBEEP!");
        vehicle.moveBackward();
    }

    @Override
    public void turnLeft() {
        logger.info("Moving Left! BEEPBEEP!");
        vehicle.turnLeft();
    }

    @Override
    public void turnRight() {
        logger.info("Moving Forward! BEEPBEEP!");
        vehicle.turnRight();
    }
}
