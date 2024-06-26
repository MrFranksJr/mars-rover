import {useContext} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";
import RoverInfo from "./RoverInfo.tsx";
import styles from "../../../styles/SimulationState.module.css"

function SimulationState() {
    const {simulation} = useContext(SimulationContext);
    const roversInSimulation = simulation.roverList;

    return (
        <>
            <div id="simulationState" className={styles.simulationState}>
                <h2 className={styles.simulationStateH2}>Current Simulation state:</h2>
                <p className={styles.simulationStateP}>The Simulation size is {simulation.simulationSize}</p>
                <p className={styles.simulationStateP}>The Simulation has {simulation.totalCoordinates} total
                    Coordinates</p>
                <hr/>
                <div className={styles.roverStates}>
                    {roversInSimulation.length === 0 ? (
                            <div className={styles.roverStates}>There are currently no Rovers in the Simulation</div>)
                        :
                        (roversInSimulation.map(rover => (
                                <RoverInfo
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
            </div>
        </>
    )
}

export default SimulationState