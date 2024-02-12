package io.tripled.marsrover.business;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.business.api.*;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private final MarsRoverApi marsRoverApi;
    private final SimulationRepository simulationRepository;

    public StepDefinitions() {
        simulationRepository = new InMemSimulationRepo();
        marsRoverApi = new MarsRoverController(simulationRepository);
    }

    @Given("A simulation of size {int}")
    public void aSimulationOfSizeSimSize(int simulationSize) {
        marsRoverApi.initializeSimulation(simulationSize, createSimulationCreationPresenter());
    }

    @And("We land a rover on coordinates {int} {int}")
    public void weLandARoverOnCoordinatesXY(int x, int y) {
        final var coordinate = new Coordinate(x, y);
        marsRoverApi.landRover(coordinate, createLandingPresenter());
    }

    @When("We give the Rover {string} the Instruction {string} {int}")
    public void weGiveTheRoverTheInstructionAmount(String roverName, String instruction, int steps) {
        String directionString = instruction.substring(0, 1);
        final List<RoverMove> moves = List.of(new RoverMove(roverName, directionString, steps));

        marsRoverApi.moveRover(moves, createRoverMovePresenter());
    }

    private static RoverMovePresenter createRoverMovePresenter() {
        return new RoverMovePresenter() {
            @Override
            public void moveRoverSuccessful(RoverState roverState) {

            }

            @Override
            public void cannotMoveIfRoverDoesNotExist() {

            }

            @Override
            public String moveRoverUnsuccesful() {
                return null;
            }
        };
    }

    @When("We give the Rover {string} the Instructions")
    public void weGiveTheRoverTheInstructions(String roverName, io.cucumber.datatable.DataTable dataTable) {

        final List<RoverMove> roverMovesList = parseDataTableToRoverMovesList(roverName, dataTable);

        marsRoverApi.moveRover(roverMovesList, createRoverMovePresenter());
    }

    private static List<RoverMove> parseDataTableToRoverMovesList(String roverName, DataTable dataTable) {
        List<Map<String, String>> instructions = dataTable.asMaps(String.class, String.class);

        List<RoverMove> roverMovesList = new ArrayList<>();

        for (Map<String, String> row : instructions) {
            String command = row.get("instruction").substring(0, 1);
            int amount = Integer.parseInt(row.get("amount"));

            roverMovesList.add(new RoverMove(roverName, command, amount));
        }
        return roverMovesList;
    }

    @Then("The Rover {string} is at {int} {int} with orientation {string}")
    public void theRoverIsAtNewXNewYWithOrientation(String roverName, int newX, int newY, String heading) {
        if (simulationRepository.getSimulation().isPresent()) {
            final RoverState currentRoverState = getRoverState(roverName);
            assertEquals(roverName, currentRoverState.roverId());
            assertEquals(newX, currentRoverState.coordinate().xCoordinate());
            assertEquals(newY, currentRoverState.coordinate().yCoordinate());
            assertEquals(heading, currentRoverState.roverHeading().toString().toLowerCase());
        }
    }

    private RoverState getRoverState(String roverName) {
        final Simulation simulation = simulationRepository.getSimulation().orElseThrow();
        return simulation.takeSnapshot().getRover(roverName).orElseThrow();
    }

    @Then("No rover should be present in the simulation")
    public void no_rover_should_be_present_in_the_simulation() {
        if (simulationRepository.getSimulation().isPresent()) {
            assertEquals(0, simulationRepository.getSimulation().get().takeSnapshot().roverList().size());
        }
    }

    private static LandingPresenter createLandingPresenter() {
        return new LandingPresenter() {
            @Override
            public void landingSuccessful(RoverState state) {

            }

            @Override
            public void roverMissesSimulation(int simulationSize) {

            }

            @Override
            public void negativeCoordinatesReceived(Coordinate coordinate) {

            }
        };
    }

    private static SimulationCreationPresenter createSimulationCreationPresenter() {
        return new SimulationCreationPresenter() {
            @Override
            public void simulationCreationSuccessful(SimulationState simulationState) {

            }

            @Override
            public void simulationCreationUnsuccessful(int simulationSize) {

            }

            @Override
            public void simulationAlreadyExists(SimulationState simulationState) {

            }
        };
    }
}
