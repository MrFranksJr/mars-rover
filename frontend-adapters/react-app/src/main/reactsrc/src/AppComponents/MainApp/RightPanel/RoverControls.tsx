//RoverControls.tsx
import roverControlsStyles from '../../../styles/RoverControls.module.css'
import {SimulationContext} from "../../SimulationContext.tsx";
import RoverControl from "./RoverControl.tsx";
import appStyles from "../../../styles/App.module.css"
import {FormEvent, useContext, useState} from "react";

function RoverControls() {
    const {simulation, updateSimulation} = useContext(SimulationContext);
    const roversInSimulation = simulation.roverList;

    const [instructions, setInstructions] = useState<{ [key: string]: string }>({});

    const handleFormSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        for (let roverName of Object.keys(instructions)) {
            const instruction = instructions[roverName]
            try {
                const result = await fetch(`/api/moverover/${simulation.simulationId}/${roverName} ${instruction}`, {
                    method: 'POST',
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                });

                if (!result.ok) {
                    console.error(`Failed to move rover ${roverName}. Please try again later.`);
                } else {
                    const responseData = await result.json();
                    await updateSimulation(simulation.simulationId);
                    console.log(`Rover ${roverName} moved successfully!`);
                    console.log(responseData);
                }
            } catch (error) {
                console.error(error);
            }
        }
        setInstructions({});
    };

    const handleInstructionChange = (roverName: string, instruction: string) => {
        setInstructions(prevInstructions => ({
            ...prevInstructions,
            [roverName]: instruction
        }));
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
                                    instruction={instructions[rover.roverName] || ""}
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