Feature: Support multiple Simulations
  We should be able to support multiple simulations stored in our database.
  Description :

  Scenario: Create a single simulation
    When We create a simulation of size 10
    Then Then our database should only contain 1 Simulation with size 10
    And This simulation has a total of 121 possible coordinates