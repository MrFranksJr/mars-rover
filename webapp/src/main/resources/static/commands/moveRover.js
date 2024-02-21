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
    const dataResult = data.result
    if(dataResult.includes('has broken down')){
        await getSimulationState();
        modalDiv.innerHTML = `Rover has collided!<br/> ${dataResult}! Try again!`
        modalError()
    } else if(dataResult.includes('is already broken down and cannot move')){
        await getSimulationState();
        modalDiv.innerHTML = `Rover cannot move because it's boken down.<br/> ${dataResult}!<br/>Try again!`
        modalError()
    } else if(dataResult.includes('has collided')) {
        await getSimulationState();
        modalDiv.innerHTML = `Instruction execution stopped! <br/> ${dataResult}! Try again!`
        modalError()
    } else if(data.result === "Rover moves successfully"){
        await getSimulationState();
        modalDiv.innerHTML = "Your rover moved successfully."
        moveModal();
    } else if(data.result === "Cannot execute instructions") {
        modalDiv.innerHTML = `This instruction couldn't be executed!<br/>Try again!`
        modalError()
    }
}