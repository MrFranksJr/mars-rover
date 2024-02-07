import { generateMap } from "/data/mapData.js";

async function onLoadCreateSimulation(){
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()
    
    if( readableSimulationState.simulationSize == -1 ){
        await createSimulation();
        await getSimulationState();
    } else {
        await getSimulationState();
    }

}

async function createSimulation() {
    await fetch(`/api/createsimulation/10`, {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
}

setTimeout(() => {
    onLoadCreateSimulation();
  }, "500");



async function getSimulationState() {
    let simulationState = await fetch('/api/simulationstate')
    let readableSimulationState = await simulationState.json()  
    let roversInSimulation;

    if(readableSimulationState.roverList.length == 0){
        roversInSimulation = 'There are currently no rovers in the simulation'
        updateUIWithSimulationState(readableSimulationState, roversInSimulation);
        drawMap(readableSimulationState);
    } else {
        disableLandControls()
        disableRoverIde(readableSimulationState.roverList[0].roverName);
        roversInSimulation = `Rover ${readableSimulationState.roverList[0].roverName} is at position (${readableSimulationState.roverList[0].roverXPosition}, ${readableSimulationState.roverList[0].roverYPosition}) with heading ${readableSimulationState.roverList[0].roverHeading}`
        updateUIWithSimulationState(readableSimulationState, roversInSimulation);
        drawMap(readableSimulationState);
    }
    return simulationState;
}

function updateUIWithSimulationState(readableSimulationState, roversInSimulation){
    document.getElementById('simulationState').innerHTML = `
        <p>The simuation Size is ${readableSimulationState.simulationSize}</p>
        <p>The simulation has ${readableSimulationState.totalCoordinates} total Coordinates</p>
        <p>${roversInSimulation}</p>
        `
}

async function landRover(){
    const xCoordinate = document.getElementById("roverXCoordinate").value;
    const yCoordinate = document.getElementById("roverYCoordinate").value;
    await fetch(`/api/landrover/${xCoordinate}/${yCoordinate}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });
    await getSimulationState();
    modalDiv.innerHTML = "Your rover has successfully landed."
    moveModal();
}

async function moveRover(){
    const roverId = document.getElementById("roverId").value;
    const roverInstructions = document.getElementById("roverInstructions").value;

    if(roverId != null && roverInstructions != null ){
        await fetch(`/api/moverover/${roverId}/${roverInstructions}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }})
    }
    await getSimulationState();
    modalDiv.innerHTML = "Rover instructions successfully executed"
    document.getElementById('roverInstructions').value = "";
    moveModal();
}


function disableLandControls() {
    landRoverBtn.disabled = true;
    document.getElementById('roverXCoordinate').disabled = true;
    document.getElementById('roverYCoordinate').disabled = true;
}

function disableRoverIde(roverId){
    document.getElementById('roverId').value = roverId;
    document.getElementById('roverId').disabled = true;
}

document.querySelectorAll('form').forEach(node => {
    node.addEventListener("submit", function (event) {
        event.preventDefault()
    })
})

const modalDiv = document.getElementById('feedbackModal');
function moveModal() {
    console.log('modalfeedback handler');
    modalDiv.classList.add('activeModal');
    setTimeout(() => {
        modalDiv.classList.remove('activeModal');
      }, "2000");
}


function drawMap(readableSimulationState) {
    document.getElementById('simulationMap').innerHTML =  generateMap(readableSimulationState)
}

///////BUTTONS
const landRoverBtn = document.getElementById('landRoverBtn');
const moveRoverBtn = document.getElementById('moveRoverBtn');

///////EVENT LISTENERS
landRoverBtn.addEventListener('click', landRover)
moveRoverBtn.addEventListener('click', moveRover)