Feature: Rovers now have hitpoints
  We should be able to support an amount of hitpoints on Rovers. Each time Rovers collide with each other, the Rover that moved into the other Rover should receive 1 point of damage. When the Rover's hitpoints reach 0, the Rover can no longer move and becomes BROKEN.
  Description :

  Scenario: Rover lands with full hitpoints
    When We land a rover on the surface of our Simulation
    Then it lands with a total of 5 hitpoints
    And its status is set to "OPERATIONAL" meaning it's ready to move

  Scenario: Rover loses hitpoints when colliding
    Given a simulation with two Rovers next to each other and facing each other
    When we move Rover "R2" into Rover "R1"
    Then Rover "R2" loses 1 hitpoint and ends up with 4 hitpoints
    And Rover "R1" does not lose any hitpoints, and is left with 5 hitpoints
    And Both Rovers remain in status "OPERATIONAL"


  Scenario: Rovers hitting 0 healthpoints become BROKEN
    Given a simulation with two Rovers next to each other and facing each other
    When We give the Rover R2 the Instructions
      | instruction | amount |
      | forward     | 1      |
      | forward     | 1      |
      | forward     | 1      |
      | forward     | 1      |
      | forward     | 1      |
    Then The Rover "R2" has 0 hitpoints and become "BROKEN"
    And The Rover "R1" still has 5 hitpoints and still "OPERATIONAL"
    And when we try to move Rover "R2" the rover does not move anymore


  Scenario: Rovers landing on top of each other both become BROKEN
    Given a simulation with one Rover "R1" on the surface
    When We land a second rover "R2" on top of the first rover
    Then The Rover "R2" has 0 hitpoints and becomes "BROKEN"
    And The Rover "R1" has 0 hitpoints and becomes "BROKEN"
    And when we try to move Rover "R2" the rover does not move anymore
    And when we try to move Rover "R1" the rover does not move anymore