import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv, modalError, roverInstructionsField } from "/index.js";

export { moveRover }

async function moveRover(){
    const roverId = document.getElementById("roverId").value;
    const roverInstructions = document.getElementById("roverInstructions").value;

    if(roverId != "" && roverInstructions != "" ){
        let result = await fetch(`/api/moverover/${roverId}/${roverInstructions}` , {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }});

        let data = await result.json();

        await awaitRoverMoveFeedback(data);
    } else {
        modalDiv.innerHTML = `This instruction couldn't be executed!<br/>Try again!`
        modalError()
    }
}

async function awaitRoverMoveFeedback(data){
    if(data.result == "Rover move successful"){
        await getSimulationState();
        modalDiv.innerHTML = "Your rover has successfully moved."
        moveModal();
    } 
    else if(data.result == "Rover move unsuccessful") {
        modalDiv.innerHTML = `This instruction couldn't be executed!<br/>Try again!`
        modalError()
    }

    roverInstructionsField.value = "";
}