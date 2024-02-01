package io.tripled.marsrover.business.domain.rover;

import org.junit.jupiter.api.Test;

class LoggingDecoratorTest {

    @Test
    void beepingTest() {
        Rover rover = new Rover("R1", 5, 6, 20);
        var beepingRover = LoggingDecorator.decorate(rover);
        beepingRover.moveForward();
    }

    @Test
    void beepingTest2() {
        Rover rover = new Rover("R1", 5, 6, 20);
        var beepingRover = LoggingDecorator.decorate(rover);
        beepingRover.moveBackward();
    }

    @Test
    void beepingTest3() {
        Rover rover = new Rover("R1", 5, 6, 20);
        var beepingRover = LoggingDecorator.decorate(rover);
        beepingRover.turnLeft();
    }

    @Test
    void beepingTest4() {
        Rover rover = new Rover("R1", 5, 6, 20);
        var beepingRover = LoggingDecorator.decorate(rover);
        beepingRover.turnRight();
    }
}