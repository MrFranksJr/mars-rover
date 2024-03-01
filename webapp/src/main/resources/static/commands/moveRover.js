import { getSimulationState } from "./getSimulationState.js";
import { moveModal, modalDiv, modalError, buildRoverInstructionControls } from "../index.js";

export { moveRover }

async function moveRover(){
    const roverInstructionFields = document.getElementById('roverInstructionFields');
    const childrenOfInstructions = roverInstructionFields.children
    let instructionString = ""

    for (let i = 0; i < childrenOfInstructions.length; i++) {
        let child = childrenOfInstructions[i]
        let inputElement = child.querySelector('.roverInstructions')
        let roverInstructions = inputElement.value.trim();
        let roverId = inputElement.getAttribute('name');
        
        if (roverInstructions !== "") {
            instructionString += `${roverId} ${roverInstructions} `
        }
    }

    if (instructionString !== "") {
        const simulationSelectionField = document.getElementById('simulations')
        const simulationId = simulationSelectionField.value;
        const result = await fetch(`/api/moverover/${simulationId}/${instructionString}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }});

        const data = await result.json();

        await awaitRoverMoveFeedback(data)
        await buildRoverInstructionControls()
    }
    else {
        modalDiv.innerHTML = `This instruction field is empty!<br/>Try again!`
        modalError()
    }
}

async function awaitRoverMoveFeedback(data){
    await getSimulationState(data.simulationId);
    if(data.roverMove === "SUCCESS"){
        modalDiv.innerHTML = `Rover ${data.roverId} moved successfully.`
        moveModal();
    } else if(data.roverMove === "BROKEN"){
        modalDiv.innerHTML = `Rover ${data.roverId} has collided!<br/>Rover ${data.roverId} is now broken!<br/>Try again!`
        modalError()
    } else if(data.roverMove === "ALREADY_BROKEN"){
        modalDiv.innerHTML = `Rover ${data.roverId} cannot move because it's broken.`
        modalError()
    } else if(data.roverMove === "COLLIDED") {
        modalDiv.innerHTML = `Instruction execution stopped!<br/>Rover ${data.roverId} has suffered a collision! Try again!`
        modalError()
    } else if(data.roverMove === "ERROR") {
        modalDiv.innerHTML = `This instruction couldn't be executed!<br/>Try again!`
        modalError()
    } else {
        modalDiv.innerHTML = `Something went wrong!<br/>Try again!`
        modalError()
    }
}