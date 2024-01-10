# Level 4 Use cases

Now that we have multiple rovers running around at the same time in our simulation, it is perhaps time to introduce Time :-)

## Introducing Time

The simulation needs a notion of time.

+ When the simulation is created we start at T0. When a Tempo passes it goes to T1, T2 etc...
+ Landing a Rover, executing a Rover Command all takes one Tempo.
+ Multiple Rovers can execute a single instruction at the same time.
+ For diagnostic purposes we can retrieve an event log from the simulation with the time present.

```bash
**************************
**    Rover       **
**************************
[Please enter a command] : land 1 1
[T0] Rover R1 was launched successfully to [1,1]
**** time changed T0 -> T1 ****
[T1] Rover R1 landed successfully at [1,1] and is facing NORTH

[Please enter a command] : land 2 2
[T1] Rover R2 was launched successfully to [2,2]
**** time changed T1 -> T2 ****
[T2] Rover R2 landed successfully at [2,2] and is facing NORTH

[Please enter a command] : m
   |----------------------------------|
 10| .  .  .  .  .  .  .  .  .  .  .  |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  .  .  .  .  .  .  .  .  .  .  |
 2 | .  . ^R2 .  .  .  .  .  .  .  .  |
 1 | . ^R1 .  .  .  .  .  .  .  .  .  |
 0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |

[Please enter a command] : p
***************************************************************************************************************************************************
*   Print API overview            | {P}                                                       | ex: p                                             *
*   Print current time            | {t}                                                       | ex: t                                             *
*   Print state of simulation     | {state}                                                   | ex: state                                         *
*   Print map of simulation       | {m}                                                       | ex: m                                             *
*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *
*   Drive with rover              | {Rx {fn|bn|ln|rn}}                                        | ex: R1 f5 l2 b                                    *
*                                                                                             | Rover with id R1                                  *
*                                                                                             | drive forward 5 steps                             *
*                                                                                             | turn left twice                                   *
*                                                                                             | go back one step                                  *
*                                                                                             |                                                   *
*   Quit the application          | {Q}                                                       | ex: q                                             *
***************************************************************************************************************************************************

[Please enter a command] : t
The current time in the simulation is T2

[Please enter a command] : R1 f
[T2] Rover R1 received instructions
[T2] Rover R1 is moving forward
**** time changed T2 -> T3 ****
[T3] Rover R1 is now located at [1,2]
[T3] Rover R1 executed all instructions. Awaiting new ones...
**** time changed T3 -> T4 ****

[Please enter a command] : t
The current time in the simulation is T4

[Please enter a command] : m
   |----------------------------------|
 10| .  .  .  .  .  .  .  .  .  .  .  |
 9 | .  .  .  .  .  .  .  .  .  .  .  |
 8 | .  .  .  .  .  .  .  .  .  .  .  |
 7 | .  .  .  .  .  .  .  .  .  .  .  |
 6 | .  .  .  .  .  .  .  .  .  .  .  |
 5 | .  .  .  .  .  .  .  .  .  .  .  |
 4 | .  .  .  .  .  .  .  .  .  .  .  |
 3 | .  .  .  .  .  .  .  .  .  .  .  |
 2 | . ^R1^R2 .  .  .  .  .  .  .  .  |
 1 | .  .  .  .  .  .  .  .  .  .  .  |
 0 | .  .  .  .  .  .  .  .  .  .  .  |
---|----------------------------------|
   | 0  1  2  3  4  5  6  7  8  9  10 |
```

## Programming Rovers

We should be able to program the Rovers with instructions that they then execute when being instructed to do so. So we want to be able to send a program towards
the Rovers that can then be executed when given the signal. This will allow us to have multiple Rovers executing instructions at the same time, not in sequence.

We introduce two new commands. A register command to program the rovers and an execute command that executes the stored programs of the rovers.

```bash
**************************
**    Rover       **
**************************
[Please enter a command] : p
***************************************************************************************************************************************************
*   Print API overview            | {P}                                                       | ex: p                                             *
*   Print current time            | {t}                                                       | ex: t                                             *
*   Print state of simulation     | {state}                                                   | ex: state                                         *
*   Print map of simulation       | {m}                                                       | ex: m                                             *
*   Land a new rover              | {land {x} {y}}                                            | ex: land 1 5                                      *
*   Drive with rover              | {Rx {fn|bn|ln|rn}}                                        | ex: R1 f5 l2 b                                    *
*                                                                                             | Rover with id R1                                  *
*                                                                                             | drive forward 5 steps                             *
*                                                                                             | turn left twice                                   *
*                                                                                             | go back one step                                  *
*   Register instructions on rover| {set Rx {fn|bn|ln|rn}}                                    | ex: set R1 f5                                     *
*                                                                                             | Register instructions on Rover with id R1         *
*                                                                                             | drive forward 5 steps                             *
*   Run program set on rover      | {run Rx {Ry}}                                             | ex: run R1 R2                                     *
*                                                                                             | Run pre set instructions on Rover with id R1,Ry   *
*                                                                                             |                                                   *
*   Quit the application          | {Q}                                                       | ex: q                                             *
***************************************************************************************************************************************************

```

## Screencast

Below an example of how it should look in level 4

![asciicast](/examples/level-four.svg)
