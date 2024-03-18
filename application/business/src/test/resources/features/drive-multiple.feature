Feature: Driving around multiple Rovers
  We want to be able drive around with multiple Rover(s) inside the simulation at the same time. Our Rovers know four simple instructions: Go forward, go backward, Turn Left and Turn Right. Each command takes one tempi to execute. With each command we can also pass how many times we wish to execute it. These basic commands can then be combined to pass a whole InstructionSet in one go. Since we are driving around on a sphere, the Rover can not fall of the map, or hit world boundaries. When the Rover reaches the end of the coordinates, it wraps back to the other side.

  Scenario: We can give Rover instructions by Id
    Given A simulation of size 10
    And We land a rover on coordinates 0 0
    And We land a rover on coordinates 5 5
    When We give the Rover "R2" the Instructions
      | instruction | amount |
      | forward     | 2      |
      | right       | 1      |
      | back        | 1      |
      | right       | 2      |
      | back        | 3      |
      | left        | 3      |
    Then The Rover "R2" is at 7 7 with orientation "north"
    And The Rover "R1" is at 0 0 with orientation "north"

  Scenario: Rovers colliding stops movement
    Given A simulation of size 10
    And We land a rover on coordinates 5 5
    And We land a rover on coordinates 5 4
    When We give the Rover "R2" the Instructions
      | instruction | amount |
      | forward     | 3      |
      | backward    | 1      |
    Then The Rover "R2" is at 5 4 with orientation "north"
    And The Rover "R1" is at 5 5 with orientation "north"

  Scenario: Rovers colliding stops backward
    Given A simulation of size 10
    And We land a rover on coordinates 5 5
    And We land a rover on coordinates 5 4
    When We give the Rover "R1" the Instructions
      | instruction | amount |
      | back        | 3      |
      | forward        | 1      |
    Then The Rover "R2" is at 5 4 with orientation "north"
    And The Rover "R1" is at 5 5 with orientation "north"


  Scenario: Rovers colliding stops movement with more complex movement
    Given A simulation of size 10
    And We land a rover on coordinates 5 5
    And We land a rover on coordinates 5 4
    When We give the Rover "R2" the Instructions
      | instruction | amount |
      | forward     | 3      |
      | left        | 1      |
      | forward     | 3      |
    Then The Rover "R2" is at 5 4 with orientation "north"
    And The Rover "R1" is at 5 5 with orientation "north"


  Scenario: We can drive multiple Rovers in one go
    Given A simulation of size 10
    And We land a rover on coordinates 5 5
    And We land a rover on coordinates 4 4
    When We give the Rovers the Instructions
      | roverId | instruction | amount |
      | R2      | forward     | 3      |
      | R1      | forward     | 3      |
      | R1      | left        | 1      |
      | R1      | forward     | 3      |
      | R2      | left        | 1      |
      | R2      | forward     | 3      |
    Then The Rover "R2" is at 1 7 with orientation "west"
    And The Rover "R1" is at 2 8 with orientation "west"
    And The timeline is
      | Timestamp | RoverId | x | y | heading |
      | T0        | R1      | 5 | 5 | N       |
      | T0        | R2      | 4 | 4 | N       |
      | T1        | R1      | 5 | 6 | N       |
      | T1        | R2      | 4 | 5 | N       |
      | T2        | R1      | 5 | 7 | N       |
      | T2        | R2      | 4 | 6 | N       |
      | T3        | R1      | 5 | 8 | N       |
      | T3        | R2      | 4 | 7 | N       |
      | T4        | R1      | 5 | 8 | W       |
      | T4        | R2      | 4 | 7 | W       |
      | T5        | R1      | 4 | 8 | W       |
      | T5        | R2      | 3 | 7 | W       |
      | T6        | R1      | 3 | 8 | W       |
      | T6        | R2      | 2 | 7 | W       |
      | T7        | R1      | 2 | 8 | W       |
      | T7        | R2      | 1 | 7 | W       |


  Scenario: We can drive multiple Rovers in one go with collisions
    Given A simulation of size 10
    And We land a rover on coordinates 5 5
    And We land a rover on coordinates 4 4
    And We land a rover on coordinates 4 6
    When We give the Rovers the Instructions
      | roverId | instruction | amount |
      | R2      | forward     | 3      |
      | R1      | forward     | 3      |
      | R1      | left        | 1      |
      | R1      | forward     | 3      |
      | R2      | left        | 1      |
      | R2      | forward     | 3      |
    Then The Rover "R1" is at 2 8 with orientation "west"
    And The Rover "R2" is at 4 5 with orientation "north"
    And The Rover "R3" is at 4 6 with orientation "north"


