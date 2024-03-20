package io.tripled.marsrover.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.vocabulary.*;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class HitpointsStepDefinitions {
    private final SimulationRepository simulationRepository;
    private final MarsRoverApi marsRoverApi;
    private SimulationId simulationId;

    public HitpointsStepDefinitions() {
        simulationRepository = new DummySimulationRepository();
        marsRoverApi = new MarsRoverController(simulationRepository);
    }

    @When("We land a rover on the surface of our Simulation")
    public void weLandARoverInSimulation() {
        marsRoverApi.initializeSimulation(10, LoggingSimulationCreationPresenter.INSTANCE);
        simulationId = simulationRepository.getSimulationSnapshots().orElseThrow().getFirst().id();
        landRoverAt(new Coordinate(5, 5));
    }

    @Then("it lands with a total of {int} hitpoints")
    public void weLandWithTotalHitpoints(int hitpoints) {
        int actualHitpoints = simulationRepository.getSimulation(simulationId).orElseThrow().roverList().getFirst().hitpoints();
        Assertions.assertEquals(hitpoints, actualHitpoints);
    }

    @And("its status is set to {string} meaning it's ready to move")
    public void roverStatusIsOperational(String operationalStatus) {
        String actualStatus = simulationRepository.getSimulation(simulationId).orElseThrow().roverList().getFirst().healthState().toString();
        Assertions.assertEquals(operationalStatus, actualStatus);
    }

    @Given("a simulation with two Rovers next to each other and facing each other")
    public void aSimulationWithTwoRoversNextToEachOtherAndFacingEachOther() {
        marsRoverApi.initializeSimulation(10, LoggingSimulationCreationPresenter.INSTANCE);
        simulationId = simulationRepository.getSimulationSnapshots().orElseThrow().getFirst().id();
        landRoverAt(new Coordinate(5, 5));
        marsRoverApi.landRover(simulationId.toString(), new Coordinate(5, 4), LoggingLandingPresenter.INSTANCE);
    }

    @When("we move Rover {string} into Rover {string}")
    public void weMoveRover1IntoRover(String r1, String r2) {
        InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves("R2", new RoverMove(Direction.FORWARD, 1))
                .build();
        marsRoverApi.executeMoveInstructions(simulationId.toString(), instructionBatch, LogginRoverMovePresenter.INSTANCE);
    }

    @Then("Rover {string} loses {int} hitpoint and ends up with {int} hitpoints")
    public void roverLosesHitpointAndEndsUpWithHitpoints(String rover1, int lostHp, int expectedHp) {
        int actualHitpoints = simulationRepository.getSimulation(simulationId).orElseThrow().roverList().get(1).hitpoints();
        Assertions.assertEquals(expectedHp, actualHitpoints);
    }

    @And("Rover R{int} does not lose any hitpoints, and is left with {int} hitpoints")
    public void roverRDoesNotLoseAnyHitpointsAndIsLeftWithHitpoints(int arg0, int arg1) {

    }

    @And("Rover {string} does not lose any hitpoints, and is left with {int} hitpoints")
    public void roverDoesNotLoseAnyHitpointsAndIsLeftWithHitpoints(String rover2, int expectedHp) {
        int actualHitpoints = getRoverHp(rover2);
        Assertions.assertEquals(expectedHp, actualHitpoints);
    }

    @And("Both Rovers remain in status {string}")
    public void bothRoversRemainInStatus(String status) {
        String rover1Status = simulationRepository.getSimulation(simulationId).orElseThrow().roverList().getFirst().healthState().toString();
        String rover2Status = simulationRepository.getSimulation(simulationId).orElseThrow().roverList().get(1).healthState().toString();
        Assertions.assertEquals(status, rover1Status);
        Assertions.assertEquals(status, rover2Status);
    }

    @When("We give the Rover R2 the Instructions")
    public void weGiveTheRoverRTheInstructions(io.cucumber.datatable.DataTable dataTable) {
        final List<Map<String, String>> instructions = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : instructions) {
            final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();
            final String roverId = "R2";
            final String command = row.get("instruction").substring(0, 1);
            final int amount = Integer.parseInt(row.get("amount"));
            final RoverMove roverMove = new RoverMove(command, amount);
            instructionBatch.addRoverMoves(roverId, roverMove);
            marsRoverApi.executeMoveInstructions(String.valueOf(simulationId), instructionBatch.build(), LogginRoverMovePresenter.INSTANCE);
        }
    }

    @Then("The Rover {string} has {int} hitpoints and become {string}")
    public void theRoverHasHitpointsAndBecome(String r2, int expectedHp, String expectedStatus) {
        String rover2Status = getRoverState(r2);
        int rover1Hp = getRoverHp(r2);
        Assertions.assertEquals(expectedHp, rover1Hp);
        Assertions.assertEquals(expectedStatus, rover2Status);
    }

    @And("The Rover {string} still has {int} hitpoints and still {string}")
    public void theRoverStillHasHitpointsAndStill(String r1, int expectedHp, String expectedStatus) {
        String rover1Status = getRoverState(r1);
        int rover1Hp = getRoverHp(r1);
        Assertions.assertEquals(expectedHp, rover1Hp);
        Assertions.assertEquals(expectedStatus, rover1Status);
    }

    @And("when we try to move Rover {string} the rover does not move anymore")
    public void whenWeTryToMoveRoverTheRoverDoesNotMoveAnymore(String roverId) {
        Coordinate originalLocation = simulationRepository.getSimulation(simulationId).orElseThrow().getRover(roverId).orElseThrow().coordinate();
        InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(roverId, new RoverMove(Direction.BACKWARD, 4))
                .build();
        marsRoverApi.executeMoveInstructions(simulationId.toString(), instructionBatch, LogginRoverMovePresenter.INSTANCE);

        Coordinate actualCoordinate = simulationRepository.getSimulationSnapshots().orElseThrow().getFirst().getRover(roverId).orElseThrow().coordinate();

        Assertions.assertEquals(originalLocation, actualCoordinate);

    }

    @Given("a simulation with one Rover {string} on the surface")
    public void aSimulationWithOneRoverOnTheSurface(String rover1) {
        marsRoverApi.initializeSimulation(10, LoggingSimulationCreationPresenter.INSTANCE);
        simulationId = simulationRepository.getSimulationSnapshots().orElseThrow().getFirst().id();
        landRoverAt(new Coordinate(5, 5));
    }

    @When("We land a second rover {string} on top of the first rover")
    public void weLandASecondRoverOnTopOfTheFirstRover(String rover2) {
        landRoverAt(new Coordinate(5, 5));
    }


    @Then("The Rover {string} has {int} hitpoints and becomes {string}")
    public void theRoverHasHitpointsAndBecomes(String roverName, int expectedHp, String expectedStatus) {
        String roverStatus = getRoverState(roverName);
        int roverHp = getRoverHp(roverName);

        Assertions.assertEquals(expectedHp, roverHp);
        Assertions.assertEquals(expectedStatus, roverStatus);
    }

    private void landRoverAt(Coordinate coordinate) {
        marsRoverApi.landRover(simulationId.toString(), new Coordinate(5, 5), LoggingLandingPresenter.INSTANCE);
    }

    private int getRoverHp(String roverName) {
        return simulationRepository.getSimulation(simulationId).orElseThrow().getRover(roverName).orElseThrow().hitpoints();
    }

    private String getRoverState(String roverName) {
        return simulationRepository.getSimulation(simulationId).orElseThrow().getRover(roverName).orElseThrow().healthState().toString();
    }
}

