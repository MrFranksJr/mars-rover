# Level 1 Use cases

## Initialization

### Create a simulated world

When starting the simulation, the system ask us to specify how large we want the simulation to be.

```batch
**************************
**    MarsRover       **
**************************
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] : 5
Simulation with max coordinate [5] created successfully. Simulation contains [36] coordinates

[Please enter a command] :
```

### Handle bad simulation input

As stated before, the system should handle incorrect input properly.

```batch
**************************
**    MarsRover       **
**************************
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] : bad
[bad] is an invalid Simulation maxCoordinate
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] :
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] : -45
[-45] is an invalid Simulation maxCoordinate
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] : 42
Simulation with max coordinate [42] created successfully. Simulation contains [1849] coordinates

[Please enter a command] :
```

## Display API

### Display API command

To assist the user, we need a display API command that they can invoke explicitly by typing P, case-insensitive.

```batch
[Please enter a command] : p
***************************************************************************************************************************************************
*   Print state of simulation     | {state}                                                   | ex: state                                         *
*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *
*   Quit the application          | {Q}                                                                                                           *
*   Print API overview            | {P}                                                                                                           *
***************************************************************************************************************************************************

[Please enter a command] : P
***************************************************************************************************************************************************
*   Print state of simulation     | {state}                                                   | ex: state                                         *
*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *
*   Quit the application          | {Q}                                                                                                           *
*   Print API overview            | {P}                                                                                                           *
***************************************************************************************************************************************************

[Please enter a command] :
```

### Display API by default

The APi should also be printed when no command is given and simply enter is being pressed

```batch
[Please enter a command] :
***************************************************************************************************************************************************
*   Print state of simulation     | {state}                                                   | ex: state                                         *
*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *
*   Quit the application          | {Q}                                                                                                           *
*   Print API overview            | {P}                                                                                                           *
***************************************************************************************************************************************************
```

### Handle invalid command by displaying API

When an invalid command is passed on, an error message should be displayed and the api should also be displayed.

```batch
[Please enter a command] : bad command
Invalid command [bad command]
***************************************************************************************************************************************************
*   Print state of simulation     | {state}                                                   | ex: state                                         *
*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *
*   Quit the application          | {Q}                                                                                                           *
*   Print API overview            | {P}                                                                                                           *
***************************************************************************************************************************************************

[Please enter a command] :
```

## A proper (q)uit command

Although the application runs in a console, we want to present the user with a proper quit command.

```batch
[Please enter a command] : q
Quiting application
```

Note that we want to be able to quit at any time. So the quit command (q) should always work. Even when initialising.

```batch
**************************
**    MarsRover       **
**************************
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] : q
Quiting application
```

## Landing

### Land a rover at specific coordinates

To populate the simulation we need to land a rover on it. We need to be able to land a rover at a specific coordinate. For simplicity, the direction it will be facing will always be North. The rover is able to report its coordinates and orientation after landing.

```batch
**************************
**    MarsRover       **
**************************
Land at 5-1
Rover R1 landed at (5,1) and is facing North

[Please enter a command] :
```

We will identify the Rover with R1. For the moment we only allow one rover in the simulation, but this will change! For this level this isn't yet important. But the land command should already display the id of the Rover.

### Launching a rover to invalid coordinates

When we want to launch the Rover at given coordinates, we must be sure that they are valid coordinates.

```batch
**************************
**    MarsRover       **
**************************
Determine the maxCoordinate of the simulation by setting the maximum coordinate [0-100]
[Enter max coordinate] : 10
Simulation with max coordinate [10] created successfully. Simulation contains [121] coordinates

[Please enter a command] : land 100 100
The coordinate [100,100] is not a valid coordinate for the planet with max coordinate 10

```

### Handle bad landing input

As stated before, the system should handle incorrect input properly.

```batch
[Please enter a command] : land a
Unable to parse coordinates for landing. Expected [x y] but was [ a]

[Please enter a command] : land a b
Unable to parse coordinates for landing. Expected two positive numbers [x y] but was [a b]

[Please enter a command] : land 1 b
Unable to parse coordinates for landing. Expected two positive numbers [x y] but was [1 b]

[Please enter a command] : land a 1
Unable to parse coordinates for landing. Expected two positive numbers [x y] but was [a 1]

[Please enter a command] : land -1 2
Invalid coordinates for landing. They must be greater than zero but were [-1 2]

[Please enter a command] : land 1 1
Rover landed successfully at [1,1] and is facing NORTH

[Please enter a command] :
```

## Print state of the simulation

At any given time we need to be able to know the state of the simulation.

```batch
**************************
**    MarsRover       **
**************************
[Please enter a command] : state
Simulation has maxCoordinate 42 with a total of 1849 coordinates.
Rover at Coordinates[x=1, y=1] is facing NORTH

[Please enter a command] :
```

## Screencasts

Here are some screencast of how the application should work upon completion.

### Happy path

The normal usage for level one

![happypath](/examples/level-one.svg)]

### Bad input

How bad input should be handled

![invalidentries](/examples/level-one-invalid.svg)

