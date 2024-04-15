# Level 3 Use cases

Now that we can drive around in our simulation, lets make it more interesting.
It's time that we remove the limit of one Rover. Let's allow up to 9 rovers. So that we can have Rovers with ids R1, R2, ..., R9
Having more rovers adds some more constraints and behaviour in our simulation. Because only one rover can occupy a spot at the same time, it is possible that
they collide. Therefore, we will need to introduce the notion of hit points. So a Rover can take damage and ultimately be rendered unusable.

## Hit points

+ A rover now has 5 hit points. When a Rover has Zero hit points left it becomes unusable and will no longer be able to process instructions. They are broken.
+ Two Rovers can not occupy the same space by driving. But when a Rover lands on top of another Rover they both occupy that location, And are both broken :-)

## Landing Multiple Rovers

+ We can land multiple rovers.
+ When a Rover is launched to the same location as another Rover, the launched Rover crashes on the existing Rover. They both receive 100 damage. Making both of
  them unusable.

## Driving multiple Rovers

+ A driving instruction sequence can be sent to multiple Rovers at the same time. [R1 f5 l b2 R4 b3 R2 l f2 R3 f2] is a valid command instructing four rovers to
  drive, in the order they appear in the sequence. Rover 1 will first execute its instructions, then Rover 4, Rover 2 and finally Rover 3.
+ When the state of the simulation is printed, it would be nice if the Rovers were shown sequentially by RoverId
+ When a Rover drives into another Rover, it doesn't take any damage, but comes to a full stop. It clears all other instructions and is ready to receive new
  instructions. The Rover that got hit however, does receive damage from one hit point

## Screencast

Below an example of how it should look

![asciicast](/examples/level-three.svg)

## Examples

### Landing Multiple Rovers

We should be able to land multiple Rovers in the Simulation. Below is an example of three Rovers landing properly, next to each other.

```bash
**************************
**    Rover       **
**************************
Determine the grid size of the simulation [0-100]
[Enter grid size] : 10
Simulation with max coordinate [10] created successfully. Simulation contains [121] coordinates

[Please enter a command] : land 1 1
Rover R1 was launched successfully to [1,1]
Rover R1 landed successfully at [1,1] and is facing NORTH

[Please enter a command] : land 2 2
Rover R2 was launched successfully to [2,2]
Rover R2 landed successfully at [2,2] and is facing NORTH

[Please enter a command] : land 3 3
Rover R3 was launched successfully to [3,3]
Rover R3 landed successfully at [3,3] and is facing NORTH

[Please enter a command] : m
   |----------------------------------|
 10| .  .  .  .  .  .  .  .  .  .  .  |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  .  . ^R3 .  .  .  .  .  .  .  |
 2 | .  . ^R2 .  .  .  .  .  .  .  .  |
 1 | . ^R1 .  .  .  .  .  .  .  .  .  |
 0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |
```

### Landing on top of each other

Landing another Rover on top of an existing one causes a crash. So after the example above, we land another rover on location 2 2.

```bash
[Please enter a command] : land 2 2
Rover R4 was launched successfully to [2,2]
Rover R4 crashed at [2,2]
Rover R2 received 100 points of damage
Rover R2 is broken and no longer able to execute instructions.
Rover R4 received 100 points of damage
Rover R4 is broken and no longer able to execute instructions.

[Please enter a command] : m
   |----------------------------------|
 10| .  .  .  .  .  .  .  .  .  .  .  |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  .  . ^R3 .  .  .  .  .  .  .  |
 2 | .  . <&> .  .  .  .  .  .  .  .  |
 1 | . ^R1 .  .  .  .  .  .  .  .  .  |
 0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |

[Please enter a command] : R2 f
Rover R2 was unable to execute the instructions

[Please enter a command] : R4 b
Rover R4 was unable to execute the instructions
```

### Drive Multiple Rovers

Continuing in the simulation from above, we can drive around with the Rovers that are not defect. We are unable to drive around with the defect Rovers. TODO

```bash
[Please enter a command] : R1 f3 l
Rover R1 received instructions
Rover R1 is moving forward
Rover R1 is now located at [1,2]
Rover R1 is moving forward
Rover R1 is now located at [1,3]
Rover R1 is moving forward
Rover R1 is now located at [1,4]
Rover R1 is turning left
Rover R1 is now facing WEST
Rover R1 executed all instructions. Awaiting new ones...

[Please enter a command] : R3 f r
Rover R3 received instructions
Rover R3 is moving forward
Rover R3 is now located at [3,4]
Rover R3 is turning right
Rover R3 is now facing EAST
Rover R3 executed all instructions. Awaiting new ones...

[Please enter a command] : m
|----------------------------------|
10| .  .  .  .  .  .  .  .  .  .  .  |
9 | .  .  .  .  .  .  .  .  .  .  .  |
8 | .  .  .  .  .  .  .  .  .  .  .  |
7 | .  .  .  .  .  .  .  .  .  .  .  |
6 | .  .  .  .  .  .  .  .  .  .  .  |
5 | .  .  .  .  .  .  .  .  .  .  .  |
4 | . <R1 . R3> .  .  .  .  .  .  .  |
3 | .  .  .  .  .  .  .  .  .  .  .  |
2 | .  . <&> .  .  .  .  .  .  .  .  |
1 | .  .  .  .  .  .  .  .  .  .  .  |
0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
| 0  1  2  3  4  5  6  7  8  9  10 |


[Please enter a command] : state
Simulation has maxCoordinate 10 with a total of 121 coordinates.
Rover Rover R1 at [1,4] with 5 hitpoints is facing WEST
Rover Rover R2 at [2,2] with 0 hitpoints is facing NORTH
Rover Rover R3 at [3,4] with 5 hitpoints is facing EAST
Rover Rover R4 at [2,2] with 0 hitpoints is facing NORTH
```

### Rovers collide

Landing the same 4 rovers, letting one crash on top of the other. We can then let a Rover collide with another by simply moving it forward ten steps.

```bash
[Please enter a command] : m
|----------------------------------|
10| .  .  .  .  .  .  .  .  .  .  .  |
9 | .  .  .  .  .  .  .  .  .  .  .  |
8 | .  .  .  .  .  .  .  .  .  .  .  |
7 | .  .  .  .  .  .  .  .  .  .  .  |
6 | .  .  .  .  .  .  .  .  .  .  .  |
5 | .  .  .  .  .  .  .  .  .  .  .  |
4 | . <R1 . R3> .  .  .  .  .  .  .  |
3 | .  .  .  .  .  .  .  .  .  .  .  |
2 | .  . <&> .  .  .  .  .  .  .  .  |
1 | .  .  .  .  .  .  .  .  .  .  .  |
0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
| 0  1  2  3  4  5  6  7  8  9  10 |

[Please enter a command] : R1 f10     
Rover R1 received instructions
Rover R1 is moving forward
Rover R1 is now located at [0,4]
Rover R1 is moving forward
Rover R1 is now located at [10,4]
Rover R1 is moving forward
Rover R1 is now located at [9,4]
Rover R1 is moving forward
Rover R1 is now located at [8,4]
Rover R1 is moving forward
Rover R1 is now located at [7,4]
Rover R1 is moving forward
Rover R1 is now located at [6,4]
Rover R1 is moving forward
Rover R1 is now located at [5,4]
Rover R1 is moving forward
Rover R1 is now located at [4,4]
Rover R1 is moving forward
Rover R3 took 1 damage and has 4 hit points left
Rover R1 collided with something at [3,4]
Rover R1 has stopped

[Please enter a command] : state
Simulation has maxCoordinate 10 with a total of 121 coordinates.
Rover Rover R1 at [4,4] with 5 hitpoints is facing WEST
Rover Rover R2 at [2,2] with 0 hitpoints is facing NORTH
Rover Rover R3 at [3,4] with 4 hitpoints is facing EAST
Rover Rover R4 at [2,2] with 0 hitpoints is facing NORTH

[Please enter a command] : m
   |----------------------------------|
 10| .  .  .  .  .  .  .  .  .  .  .  |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  . R3><R1 .  .  .  .  .  .  |
 3 | .  .  .  .  .  .  .  .  .  .  .  |
 2 | .  . <&> .  .  .  .  .  .  .  .  |
 1 | .  .  .  .  .  .  .  .  .  .  .  |
 0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |

```

### Command multiple Rovers in one go by sending instructions in one sequence

Landing the same 4 rovers, letting one crash on top of the other. We can then send a single line instruction to the two still functioning Rovers. Letting them
bump into one another.

```bash
**************************
**    Rover       **
**************************
Determine the grid size of the simulation [0-100]
[Enter grid size] : 10
Simulation with max coordinate [10] created successfully. Simulation contains [121] coordinates

[Please enter a command] : land 1 1
Rover R1 was launched successfully to [1,1]
Rover R1 landed successfully at [1,1] and is facing NORTH

[Please enter a command] : land 2 2
Rover R2 was launched successfully to [2,2]
Rover R2 landed successfully at [2,2] and is facing NORTH

[Please enter a command] : land 3 3
Rover R3 was launched successfully to [3,3]
Rover R3 landed successfully at [3,3] and is facing NORTH

[Please enter a command] : land 2 2
Rover R4 was launched successfully to [2,2]
Rover R4 crashed at [2,2]
Rover R2 received 100 points of damage
Rover R2 is broken and no longer able to execute instructions.
Rover R4 received 100 points of damage
Rover R4 is broken and no longer able to execute instructions.

[Please enter a command] : R1 f2 r R3 l R1 f5
Rover R1 received instructions
Rover R1 is moving forward
Rover R1 is now located at [1,2]
Rover R1 is moving forward
Rover R1 is now located at [1,3]
Rover R1 is turning right
Rover R1 is now facing EAST
Rover R1 executed all instructions. Awaiting new ones...
Rover R3 received instructions
Rover R3 is turning left
Rover R3 is now facing WEST
Rover R3 executed all instructions. Awaiting new ones...
Rover R1 received instructions
Rover R1 is moving forward
Rover R1 is now located at [2,3]
Rover R1 is moving forward
Rover R3 took 1 damage and has 4 hit points left
Rover R1 collided with something at [3,3]
Rover R1 has stopped


[Please enter a command] : m
   |----------------------------------|
 10| .  .  .  .  .  .  .  .  .  .  .  |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  . R1><R3 .  .  .  .  .  .  .  |
 2 | .  . <&> .  .  .  .  .  .  .  .  |
 1 | .  .  .  .  .  .  .  .  .  .  .  |
 0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |

[Please enter a command] : state
Simulation has maxCoordinate 10 with a total of 121 coordinates.
Rover Rover R1 at [2,3] with 5 hitpoints is facing EAST
Rover Rover R2 at [2,2] with 0 hitpoints is facing NORTH
Rover Rover R3 at [3,3] with 4 hitpoints is facing WEST
Rover Rover R4 at [2,2] with 0 hitpoints is facing NORTH
```

### A rover in each corner of the simulation

Here we land a rover in each corner, then let them switch places with a single line instruction, intentionally letting them bump into one another.

```bash
**************************
**    Rover       **
**************************
Determine the grid size of the simulation [0-100]
[Enter grid size] : 10
Simulation with max coordinate [10] created successfully. Simulation contains [121] coordinates

[Please enter a command] : land 0 0
Rover R1 was launched successfully to [0,0]
Rover R1 landed successfully at [0,0] and is facing NORTH


[Please enter a command] : land 10 0
Rover R2 was launched successfully to [10,0]
Rover R2 landed successfully at [10,0] and is facing NORTH


[Please enter a command] : land 0 10
Rover R3 was launched successfully to [0,10]
Rover R3 landed successfully at [0,10] and is facing NORTH


[Please enter a command] : land 10 10
Rover R4 was launched successfully to [10,10]
Rover R4 landed successfully at [10,10] and is facing NORTH

[Please enter a command] : m
   |----------------------------------|
 10|^R3 .  .  .  .  .  .  .  .  . ^R4 |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  .  .  .  .  .  .  .  .  .  .  |
 2 | .  .  .  .  .  .  .  .  .  .  .  |
 1 | .  .  .  .  .  .  .  .  .  .  .  |
 0 |^R1 .  .  .  .  .  .  .  .  . ^R2 |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |

[Please enter a command] : R1 f10 R2 f10 R3 f R4 f R1 f R2 f
Rover R1 received instructions
Rover R1 is moving forward
Rover R1 is now located at [0,1]
Rover R1 is moving forward
Rover R1 is now located at [0,2]
Rover R1 is moving forward
Rover R1 is now located at [0,3]
Rover R1 is moving forward
Rover R1 is now located at [0,4]
Rover R1 is moving forward
Rover R1 is now located at [0,5]
Rover R1 is moving forward
Rover R1 is now located at [0,6]
Rover R1 is moving forward
Rover R1 is now located at [0,7]
Rover R1 is moving forward
Rover R1 is now located at [0,8]
Rover R1 is moving forward
Rover R1 is now located at [0,9]
Rover R1 is moving forward
Rover R3 took 1 damage and has 4 hit points left
Rover R1 collided with something at [0,10]
Rover R1 has stopped
Rover R2 received instructions
Rover R2 is moving forward
Rover R2 is now located at [10,1]
Rover R2 is moving forward
Rover R2 is now located at [10,2]
Rover R2 is moving forward
Rover R2 is now located at [10,3]
Rover R2 is moving forward
Rover R2 is now located at [10,4]
Rover R2 is moving forward
Rover R2 is now located at [10,5]
Rover R2 is moving forward
Rover R2 is now located at [10,6]
Rover R2 is moving forward
Rover R2 is now located at [10,7]
Rover R2 is moving forward
Rover R2 is now located at [10,8]
Rover R2 is moving forward
Rover R2 is now located at [10,9]
Rover R2 is moving forward
Rover R4 took 1 damage and has 4 hit points left
Rover R2 collided with something at [10,10]
Rover R2 has stopped
Rover R3 received instructions
Rover R3 is moving forward
Rover R3 is now located at [0,0]
Rover R3 executed all instructions. Awaiting new ones...
Rover R4 received instructions
Rover R4 is moving forward
Rover R4 is now located at [10,0]
Rover R4 executed all instructions. Awaiting new ones...
Rover R1 received instructions
Rover R1 is moving forward
Rover R1 is now located at [0,10]
Rover R1 executed all instructions. Awaiting new ones...
Rover R2 received instructions
Rover R2 is moving forward
Rover R2 is now located at [10,10]
Rover R2 executed all instructions. Awaiting new ones...

[Please enter a command] : m
   |----------------------------------|
 10|^R1 .  .  .  .  .  .  .  .  . ^R2 |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  .  .  .  .  .  .  .  .  .  .  |
 2 | .  .  .  .  .  .  .  .  .  .  .  |
 1 | .  .  .  .  .  .  .  .  .  .  .  |
 0 |^R3 .  .  .  .  .  .  .  .  . ^R4 |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |

[Please enter a command] : state
Simulation has maxCoordinate 10 with a total of 121 coordinates.
Rover Rover R1 at [0,10] with 5 hitpoints is facing NORTH
Rover Rover R2 at [10,10] with 5 hitpoints is facing NORTH
Rover Rover R3 at [0,0] with 4 hitpoints is facing NORTH
Rover Rover R4 at [10,0] with 4 hitpoints is facing NORTH
```


