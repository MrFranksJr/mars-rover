Feature: Support multiple Simulations
  We should be able to support multiple simulations stored in our database.
  Description :

  Scenario: Create a single simulation
    When We create a simulation of size 10
    Then the system contains 1 Simulation with size 10
    And This simulation has a total of 121 possible coordinates


  Scenario: Creating multiple Simulations
    When We create a simulation of size 10
    And We create a simulation of size 15
    Then the system contains 2 Simulations
    And The first simulation has a size of 10 and 121 possible coordinates
    And The second simulation has a size of 15 and 256 possible coordinates