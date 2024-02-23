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
        let result = await fetch(`/api/moverover/${instructionString}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }});

        let data = await result.json();

        await awaitRoverMoveFeedback(data)
        await buildRoverInstructionControls()
    }
    else {
        modalDiv.innerHTML = `This instruction field is empty!<br/>Try again!`
        modalError()
    }
}

async function awaitRoverMoveFeedback(data){
    console.log(data)
    if(data.result === "success"){
        await getSimulationState();
        modalDiv.innerHTML = `Rover moved successfully.`
        moveModal();
    } else if(data.result === "broken"){
        await getSimulationState();
        modalDiv.innerHTML = `Rover has collided!<br/>Rover ${data.roverData.roverId} is now broken!<br/>Try again!`
        modalError()
    } else if(data.result === "alreadyBroken"){
        await getSimulationState();
        modalDiv.innerHTML = `Rover ${data.roverData.roverId} cannot move because it's broken.`
        modalError()
    } else if(data.result === "collided") {
        await getSimulationState();
        modalDiv.innerHTML = `Instruction execution stopped!<br/>Rover ${data.roverData.roverId} has suffered a collision! Try again!`
        modalError()
    } else if(data.result === "error") {
        modalDiv.innerHTML = `This instruction couldn't be executed!<br/>Try again!`
        modalError()
    }
}