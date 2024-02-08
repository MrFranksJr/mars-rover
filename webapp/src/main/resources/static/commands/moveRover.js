import { getSimulationState } from "/commands/getSimulationState.js";
import { moveModal, modalDiv, modalError, roverInstructionsField } from "/index.js";

export { moveRover }

async function moveRover(){
    const roverId = document.getElementById("roverId").value;
    const roverInstructions = document.getElementById("roverInstructions").value;

    if(roverId != null && roverInstructions != null ){
        //Check if rover instructions are correct
        const regex = /^(?:[frbl](?=.*\d)?\d* ?)*$/i
        const match = roverInstructions.match(regex)
        if (match) {
            await fetch(`/api/moverover/${roverId}/${roverInstructions}` , {
                method: "POST",
                headers: {
                    "Content-type": "application/json; charset=UTF-8"
                }});
        
            await getSimulationState();
    
            modalDiv.innerHTML = "Rover instructions successfully executed"
            roverInstructionsField.value = "";
            moveModal();
        } 
        else {
        modalDiv.innerHTML = "Cannot execute these instructions..."
        roverInstructionsField.value = "";
        modalError();
        }
    }
}