import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv, modalError, roverInstructionsField } from "/index.js";

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

        await awaitRoverMoveFeedback(data);
    }
    else {
        modalDiv.innerHTML = `This instruction field is empty!<br/>Try again!`
        modalError()
    }
}

async function awaitRoverMoveFeedback(data){
    const dataResult = data.result
    if(dataResult.includes('collided')) {
        await getSimulationState();
        modalDiv.innerHTML = `Instructions stopped! ${dataResult}!<br/>Try again!`
        modalError()
    } else if(data.result == "Rover moves successful"){
        await getSimulationState();
        modalDiv.innerHTML = "Your rover has successfully moved."
        moveModal();
    } else if(data.result == "Rover moves unsuccessful") {
        modalDiv.innerHTML = `This instruction couldn't be executed!<br/>Try again!`
        modalError()
    } 

    //roverInstructionsField.value = "";
}