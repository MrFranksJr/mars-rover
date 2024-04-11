//RoverControls.tsx
import roverControlsStyles from '../../../styles/RoverControls.module.css'
import {SimulationContext} from "../../SimulationContext.tsx";
import RoverControl from "./RoverControl.tsx";
import appStyles from "../../../styles/App.module.css"
import {FormEvent, useContext, useState} from "react";

interface Instruction {
    roverName: string;
    instruction: string;
}

function RoverControls() {
    const {simulation, updateSimulation} = useContext(SimulationContext);
    const roversInSimulation = simulation.roverList;

    const [instructions, setInstructions] = useState<Instruction[]>([]);

    const handleFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        for (let instruction of instructions) {
            try {
                const result = await fetch(`/api/moverover/${simulation.simulationId}/${instruction.roverName} ${instruction.instruction}`, {
                    method: 'POST',
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                });

                if (!result.ok) {
                    console.error(`Failed to move rover ${instruction.roverName}. Please try again later.`);
                } else {
                    const responseData = await result.json();
                    await updateSimulation(simulation.simulationId);
                    console.log(`Rover ${instruction.roverName} moved successfully!`);
                    console.log(responseData);
                }

            } catch (error) {
                console.error(error);
                // set error modal?
            }
        }
    };

    const handleInstructionChange = (roverName: string, instruction: string) => {
        setInstructions(prevInstructions => {
            const updatedInstructions = [...prevInstructions];
            const index = updatedInstructions.findIndex(inst => inst.roverName === roverName);
            if (index !== -1) {
                updatedInstructions[index] = { roverName, instruction };
            } else {
                updatedInstructions.push({ roverName, instruction });
            }
            return updatedInstructions;
        });
    };

    return (
        <>
            <form id="moveRoverControls" className={roverControlsStyles.moveRoverControls} onSubmit={handleFormSubmit}>
                <div id="roverInstructionFields" className={roverControlsStyles.roverInstructionFields}>
                    {roversInSimulation.length === 0 ? (
                            `There are currently no active Rovers in the Simulation. Land some Rovers first!`)
                        :
                        (roversInSimulation.map(rover => (
                                <RoverControl
                                    key={rover.roverName}
                                    roverName={rover.roverName}
                                    roverHeading={rover.roverHeading}
                                    roverXPosition={rover.roverXPosition}
                                    roverYPosition={rover.roverYPosition}
                                    hitPoints={rover.hitPoints}
                                    operationalStatus={rover.operationalStatus}
                                    onInstructionChange={handleInstructionChange}
                                />
                            ))
                        )}
                </div>
                <button id="moveRoverBtn" className={appStyles.moveRoverBtn} type="submit">Execute Instructions!
                </button>
            </form>
        </>
    )
}

export default RoverControls