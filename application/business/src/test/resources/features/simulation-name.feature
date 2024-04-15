Feature: Give Simulations a name
  Simulations are assigned a unique ID, but for ux reasons, it is better if users are able to name their Simulations.
  Description :

  Scenario: Retrieve the name of a Simulation
    When We create a simulation with the name "Just My Simulation"
    Then The system should show us that the Simulation has a size of 10
    And The system should show us that the Simulation's name is "Just My Simulation"