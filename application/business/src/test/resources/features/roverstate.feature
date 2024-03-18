Feature: Support retrieving information about a specific Rover
  We should be able to retrieve all information related to a Rover currently landed on the simulation
  Description :

  Scenario: Retrieve information about a Rover
    When We create a simulation
    And Land a Rover on that simulation on coordinates x=5 and y=5
    Then The system should show us that the Simulation has 1 Rover inside of it
    And the system should return its position of x=5 and y=5
    And the system should return its heading of "NORTH"