# Mars Rover Development Program

## Description

This exercise is an adaption off the well known [Mars rover kata](https://kata-log.rocks/mars-rover-kata). The full functional requirements can be found [here](REQUIREMENTS.md)

## Mobbing Tips

To pair / mob programming fluently work in fixed timed sessions.
We use mob.sh and timer.mob.sh for this

### Timing
Set a local env variable

```
MOB_TIMER_ROOM="triple-d"
```
by
```bash
export MOB_TIMER_ROOM="triple-d"
```

When you start a mob/paring session, you pass the time of the session

```
 mob start 10
```

You can then follow in your [browser](https://timer.mob.sh/triple-d#)

## BDD reports

We have added some Cucumber scenarios that are executed with each build when the integration tests are run. From those test you can also create nice html reports. In order to doe this, just run 

```bash
./marsBDD
```

this will build the application and generate the reports under mars-rover/business/target/generated-report.
You can open them from Intellij via [Cucumber reports](http://localhost:63342/mars-rover/business/target/generated-report/index.html)



## Dockerized MongoDB setup

### Install the Docker Desktop Application for your platform from
[Docker download page](https://www.docker.com/get-started/)

### Start the Docker Desktop Application

### Check if docker is available in terminal.
Check your current docker version
```bash
docker -v
```

### Spinning up our MarsRover application and the MongoDB.
Data stored in the MongoDB will be persisted in the: user HOME/marsrover/persisted-data map on your system.
```bash
docker compose up
```