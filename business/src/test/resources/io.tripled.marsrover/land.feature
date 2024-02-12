Feature: Landing in the simulation
  Before we can do anything useful, we should land a rover in the simulation. We do this be giving a land command with a specific X and Y coordinate. These coordinates need to be inside the simulation. If they aren't, then the landing fails and the Rover is "lost". A Rover should start with 5 hit points. Once the Rover has landed it is assigned a identifier automatically. The first Rover gets the id R1, the second R2 and so on. When a Rover lands on top of another Rover, both Rovers are destroyed. I.E. their hit points are reduced to zero nut they still occupy the coordinates.
  Description :

  Scenario Outline: Land a single rover
    Given A simulation of size <simSize>
    When We land a rover on coordinates <x> <y>
    Then The Rover "R1" is at <x> <y> with orientation "north"

    Examples:
      | simSize | x  | y  |
      | 10      | 5  | 5  |
      | 5       | 0  | 0  |
      | 5       | 5  | 5  |
      | 20      | 20 | 20 |
      | 20      | 10 | 10 |

  Scenario Outline: Land multiple rovers
    Given A simulation of size <simSize>
    When We land a rover on coordinates <x> <y>
    And We land a rover on coordinates <x> <a>
    And We land a rover on coordinates <a> <y>
    Then The Rover "R1" is at <x> <y> with orientation "north"
    And The Rover "R2" is at <x> <a> with orientation "north"
    And The Rover "R3" is at <a> <y> with orientation "north"

    Examples:
      | simSize | x  | y  |a  |
      | 10      | 5  | 5  |3  |
      | 5       | 0  | 0  |3  |
      | 5       | 5  | 5  |2  |
      | 20      | 20 | 20 |18 |
      | 20      | 10 | 10 |7  |


  Scenario Outline: We can not land outside of the simulation
    Given A simulation of size <simSize>
    When We land a rover on coordinates <x> <y>
    Then No rover should be present in the simulation

    Examples:
      | simSize | x  | y  |
      | 1       | 5  | 5  |
      | 10      | 20 | 0  |
      | 5       | 5  | 15 |
