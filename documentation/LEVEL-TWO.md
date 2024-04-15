# Level 2 Use cases

Now that we have an up and running system, we can add actual useful behaviour to it.

### Drive around with the rover

The rover understands the following commands

+ Move forward a given number of steps (default 1)
+ Move backward a given number of steps (default 1)
+ Turn left a given number of turns (default 1)
+ Turn right a given number of turns (default 1)

These command can be given in sequence. The following are valid commands

+ R1 f1 : Rover with id R1: drive one step forward
+ R1 b2 f2 l3 : Rover with id R1: drive two steps back, two steps forward and turn left three times

Since planets are spheres make sure to implement wrapping at edges. Connect the x edge to the other x edge, so (1,1) for
x-1 to (5,1), but connect vertical edges towards themselves in inverted coordinates, so (1,1) for y-1 connects to (5,1).

```batch
**************************
**    MarsRover       **
**************************
Land at 0-0
Rover r1 landed at (0,0) and is facing North
r1 f
Rover r1 arrived at (0,1) and is facing North
r1 l
Rover r1 is facing East
r1 f5
Rover r1 arrived at (5,1) and is facing East
r1 r2
Rover r1 is facing West
r1 f6 l f l2
Rover arrived at (0,5) and is facing North
```


### Optional: Print a visual representation of the simulation

A simple visual representation of the simulation could come in handy when we will move on to more advanced stuff. So
best to add this now. This is an optional requirement since the state command should provide everything we need.

### Extend the help message to include the new actions

Of course don't forget to update the api print message to reflect all new actions

## Screencasts

Here are some screencast of how the application should work upon completion of level two.

### Happy path

The normal usage for level two

![happypath](/examples/level-two.svg)

### Bad input

How bad input should be handled

![invalidentries](/examples/level-two-invalid.svg)
