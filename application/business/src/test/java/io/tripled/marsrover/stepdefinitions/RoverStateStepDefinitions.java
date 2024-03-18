package io.tripled.marsrover.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Assertions;

public class RoverStateStepDefinitions {
    private final SimulationRepository simulationRepository;
    private final MarsRoverApi marsRoverApi;
    private SimulationId simulationId;
    public RoverStateStepDefinitions() {
        this.simulationRepository = new DummySimulationRepository();
        marsRoverApi = new MarsRoverController(simulationRepository);
    }

    @When("We create a simulation")
    public void weCreateASimulation() {
        marsRoverApi.initializeSimulation(10, LoggingSimulationCreationPresenter.INSTANCE);
        simulationId = simulationRepository.getSimulationSnapshots().orElseThrow().getFirst().id();
    }

    @And("Land a Rover on that simulation on coordinates x={int} and y={int}")
    public void landARoverOnThatSimulationOnCoordinatesXAndY(int xCoordinate, int yCoordinate) {
        final var coordinate = new Coordinate(xCoordinate, yCoordinate);
        marsRoverApi.landRover(simulationId.toString(), coordinate, LoggingLandingPresenter.INSTANCE);
    }

    @Then("The system should show us that the Simulation has {int} Rover inside of it")
    public void theSystemShouldShowUsThatTheSimulationHasRoverInsideOfIt(int nORovers) {
        Assertions.assertEquals(nORovers, simulationRepository.getSimulation(simulationId).orElseThrow().roverList().size());
    }

    @And("the system should return its position of x={int} and y={int}")
    public void theSystemShouldReturnItsPositionOfXAndY(int xCoordinate, int yCoordinate) {
        Assertions.assertEquals(xCoordinate, simulationRepository.getSimulation(simulationId).orElseThrow().getRover("R1").orElseThrow().coordinate().xCoordinate());
        Assertions.assertEquals(yCoordinate, simulationRepository.getSimulation(simulationId).orElseThrow().getRover("R1").orElseThrow().coordinate().yCoordinate());
    }

    @And("the system should return its heading of {string}")
    public void theSystemShouldReturnItsHeadingOf(String heading) {
        Assertions.assertEquals(heading, simulationRepository.getSimulation(simulationId).orElseThrow().getRover("R1").orElseThrow().heading().toString());

    }
}
