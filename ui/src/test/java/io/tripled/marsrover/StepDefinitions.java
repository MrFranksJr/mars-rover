package io.tripled.marsrover;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.commands.Command;
import io.tripled.marsrover.cli.commands.LandCommand;
import io.tripled.marsrover.cli.commands.RoverMoveCommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {
    private final MarsRoverApi marsRoverApi;
    private final SimulationRepository simulationRepository;
    private final DummyPresenter dummyPresenter;

    public StepDefinitions() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new InMemSimulationRepo();
        marsRoverApi = new MarsRoverController(simulationRepository);
    }

    @Given("A simulation of size {int}")
    public void aSimulationOfSizeSimSize(int simulationSize) {
        simulationRepository.add(new Simulation(simulationSize));
    }

    @And("We land a rover on coordinates {int} {int}")
    public void weLandARoverOnCoordinatesXY(int x, int y) {
        LandCommand landCommand = new LandCommand(new Coordinate(x, y), marsRoverApi);
        landCommand.execute(dummyPresenter);
    }

    @When("We give the Rover {string} the Instruction {string} {int}")
    public void weGiveTheRoverTheInstructionAmount(String roverName, String instruction, int steps) {
        String directionString = instruction.substring(0, 1);
        Command roverMoveCommand = new RoverMoveCommand(List.of(new RoverMove(roverName, directionString, steps)), marsRoverApi);

        roverMoveCommand.execute(dummyPresenter);
    }

    @Then("The Rover {string} is at {int} {int} with orientation {string}")
    public void theRoverIsAtNewXNewYWithOrientation(String roverName, int newX, int newY, String heading) {
        assertEquals(roverName, simulationRepository.getSimulation().getRoverList().getFirst().getRoverName());
        assertEquals(newX, simulationRepository.getSimulation().getRoverList().getFirst().getRoverXPosition());
        assertEquals(newY, simulationRepository.getSimulation().getRoverList().getFirst().getRoverYPosition());
        assertEquals(heading, simulationRepository.getSimulation().getRoverList().getFirst().getRoverHeading().toString().toLowerCase());
    }
}