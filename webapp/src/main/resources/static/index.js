let createSimulationPromise = new Promise(function(resolve, reject) {
    fetch(`/api/createsimulation/10`, {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
  
    // after 1 second signal that the job is done with the result "done"
    setTimeout(() => resolve("done"), 1000);
  });


  createSimulationPromise.then(
    function() {
        getSimulationState()
    },
    function() {
        console.log("Something went wrong")
    }
)

async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let roversInSimulation;
    if(readableSimulationState.roverList.length == 0){
        roversInSimulation = 'There are currently no rovers in the simulation'
    } else {
        roversInSimulation = `Rover ${readableSimulationState.roverList[0].roverName} is at position (${readableSimulationState.roverList[0].roverXPosition}, ${readableSimulationState.roverList[0].roverYPosition}) with heading ${readableSimulationState.roverList[0].roverHeading}`
    }
    document.getElementById('simulationState').innerHTML = `
    <p>The simuation Size is ${readableSimulationState.simulationSize}</p>
    <p>The simulation has ${readableSimulationState.totalCoordinates} total Coordinates</p>
    <p>${roversInSimulation}</p>

    `
}

async function landRover(){
    var xCoordinate = document.getElementById("roverXCoordinate").value;
    var yCoordinate = document.getElementById("roverYCoordinate").value;
    if(xCoordinate != null && yCoordinate != null){
        let landRoverPromise = new Promise(function(resolve, reject) {
            fetch(`/api/landrover/${xCoordinate}/${yCoordinate}` , {
                    method: "POST",
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                }
            ) 

            setTimeout(() => resolve("done"), 1000);
        });
        
        landRoverPromise.then(
            function() {
                getSimulationState();
            },
            function() {
                console.log("Something went wrong")
            }
        )
    }
}

async function moveRover(){
    var roverId = document.getElementById("roverId").value;
    var roverInstructions = document.getElementById("roverInstructions").value;
    if(roverId != null && roverInstructions != null ){
        let moveRoverPromise = new Promise(function(resolve, reject) {
            fetch(`/api/moverover/${roverId}/${roverInstructions}` , {
                    method: "POST",
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                }
            ) 

            setTimeout(() => resolve("done"), 1000);
        });
        
        moveRoverPromise.then(

            function() {
                getSimulationState();
            },
            function() {
                console.log("Something went wrong")
            }
        )
    }
}