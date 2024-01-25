Feature: Driving around
  Driving around is the basic functionality we need. We want to be able drive around with our Rover(s) inside the simulation. Our Rovers know four simple instructions: Go forward, go backward, Turn Left and Turn Right. Each command takes one tempi to execute. With each command we can also pass how many times we wish to execute it. These basic commands can then be combined to pass a whole InstructionSet in one go. Since we are driving around on a sphere, the Rover can not fall of the map, or hit world boundaries. When the Rover reaches the end of the coordinates, it wraps back to the other side.

  Scenario Outline: The Rover can execute these instructions
    Given A simulation of size <simSize>
    And We land a rover on coordinates <x> <y>
    When We give the Rover "R1" the Instruction "<instruction>" <amount>
    Then The Rover "R1" is at <newX> <newY> with orientation "<newO>"

    Examples:
      | simSize | x  | y  | instruction | amount | newX | newY | newO  |
      | 10      | 5  | 5  | forward     | 5      | 5    | 10   | north |
      | 10      | 5  | 5  | forward     | 6      | 5    | 0    | north |
      | 5       | 5  | 5  | forward     | 3      | 5    | 2    | north |
      | 5       | 0  | 0  | forward     | 5      | 0    | 5    | north |
      | 20      | 20 | 20 | back        | 5      | 20   | 15   | north |
      | 20      | 10 | 10 | back        | 5      | 10   | 5    | north |
      | 20      | 10 | 10 | back        | 11     | 10   | 20   | north |
      | 5       | 0  | 0  | left        | 1      | 0    | 0    | west  |
      | 5       | 0  | 0  | left        | 2      | 0    | 0    | south |
      | 5       | 0  | 0  | left        | 3      | 0    | 0    | east  |
      | 5       | 0  | 0  | left        | 4      | 0    | 0    | north |
      | 5       | 0  | 0  | right       | 1      | 0    | 0    | east  |
      | 5       | 0  | 0  | right       | 2      | 0    | 0    | south |
      | 5       | 0  | 0  | right       | 3      | 0    | 0    | west  |
      | 5       | 0  | 0  | right       | 4      | 0    | 0    | north |

  Scenario: The Rover can execute a sequence of instructions in one go
    Given A simulation of size 10
    And We land a rover on coordinates 5 5
    When We give the Rover "R1" the Instructions
      | instruction | amount |
      | forward     | 2      |
      | right       | 1      |
      | back        | 1      |
      | right       | 2      |
      | back        | 3      |
      | left        | 3      |
    Then The Rover "R1" is at 7 7 with orientation "north"

  Scenario Outline: The Rover can not fall of the map
    Given A simulation of size <simSize>
    And We land a rover on coordinates <x> <y>
    When We give the Rover "R1" the Instruction "<instruction>" <amount>
    Then The Rover "R1" is at <newX> <newY> with orientation "<newO>"

    Examples:
      | simSize | x | y | instruction | amount | newX | newY | newO  |
      | 5       | 5 | 5 | forward     | 1      | 5    | 0    | north |
      | 5       | 5 | 0 | back        | 1      | 5    | 5    | north |
      | 5       | 0 | 5 | forward     | 1      | 0    | 0    | north |
      | 5       | 0 | 0 | back        | 1      | 0    | 5    | north |

  Scenario: The Rover can execute a sequence of instructions in one go without falling of the map
    Given A simulation of size 5
    And We land a rover on coordinates 0 0
    When We give the Rover "R1" the Instructions
      | instruction | amount |
      | forward     | 2      |
      | right       | 1      |
      | forward     | 8      |
      | right       | 1      |
      | back        | 5      |
    Then The Rover "R1" is at 2 1 with orientation "south"
