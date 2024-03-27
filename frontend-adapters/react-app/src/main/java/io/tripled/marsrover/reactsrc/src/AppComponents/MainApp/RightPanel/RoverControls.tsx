import roverControlsStyles from '../../../styles/RoverControls.module.css'
import {useContext} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";
import RoverControl from "./RoverControl.tsx";
import appStyles from "../../../styles/App.module.css"

function RoverControls() {
    const {simulation} = useContext(SimulationContext);
    const roversInSimulation = simulation?.roverList;

    return (
        <>
            <form id="moveRoverControls" className={roverControlsStyles.moveRoverControls}>
                <div id="roverInstructionFields" className={roverControlsStyles.roverInstructionFields}>
                    {roversInSimulation?.length === 0 ? (
                            `There are currently no active Rovers in the Simulation. Land some Rovers first!`)
                        :
                        (roversInSimulation?.map(rover => (
                                <RoverControl
                                    key={rover.roverName}
                                    roverName={rover.roverName}
                                    roverHeading={rover.roverHeading}
                                    roverXPosition={rover.roverXPosition}
                                    roverYPosition={rover.roverYPosition}
                                    hitPoints={rover.hitPoints}
                                    operationalStatus={rover.operationalStatus}
                                />
                            ))
                        )}
                </div>
                <button id="moveRoverBtn" className={appStyles.moveRoverBtn}>Execute Instructions!</button>
            </form>
        </>
    )
}

export default RoverControls