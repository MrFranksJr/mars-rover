import {Props, Simulation} from "../../interfaces.ts";
import {useNavigate} from "react-router";
import {ChangeEvent, useContext, useState} from "react";
import {SimulationContext} from "../SimulationContext.tsx";
import styles from '../../styles/SimulationSelectionOptions.module.css'
import CreateNewSimulation from "./CreateNewSimulation.tsx";


export function SimulationSelectionOptions({simulations}: Props) {
    let simulationId: string = "";
    let simulationName: string = "";
    const {setSimulation} = useContext(SimulationContext);
    const navigate = useNavigate();

    const [formState, setFormState] = useState("INIT")

    const handleSimulationSelection = async (e: ChangeEvent<HTMLSelectElement>) => {
        try {
            const selectedIndex = e.target.options.selectedIndex;
            simulationId = e.target.options[selectedIndex].getAttribute("data-key") as string;
            simulationName = e.target.options[selectedIndex].value;
            console.log("Simulation ID: " + simulationId);
            console.log("Simulation Name: " + simulationName);
            const simulationData = await getSimulation()
            setSimulation(simulationData)
            navigate('/app');
        } catch (error) {
            console.error()
        }
    }


    const getSimulation = async () => {
        try {
            const response = await fetch(`/api/simulationstate/${simulationId}`, {
                headers: {'Content-Type': 'application/json'}
            });
            if (!response.ok) {
                console.error(`Error getting simulationstate for SimulationId ${simulationId}`)
                new Error(`Failed to get simulation state: ${response.statusText}`);
            }
            const data = await response.json();
            return {
                simulationName: data.simulationName,
                simulationId: data.simulationId,
                simulationSize: data.simulationSize,
                totalCoordinates: data.totalCoordinates,
                roverList: data.roverList
            }
        } catch (error) {
            console.error("Error getting simulation state:", error);
            throw error;
        }
    }


    /*const handleCreateSimulation = async () => {
        try {
            const response = await fetch('/api/createsimulation/10', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            });
            const data = await response.json();
            simulationId = data.simulationId;
            const simulationData = await getSimulation()
            setSimulation(simulationData)
            navigate('/app');
        } catch (error) {
            console.error("Error creating new simulation:", error);
        }
    }*/


    return (
        <>
            <div className={styles.simulationOptions}>
                {formState === "INIT" && (
                    <form className={styles.simulationSelectionForm}>
                        <label htmlFor="simulations" className={styles.labelStyle}>Select an existing Simulation</label>
                        <div className="customSelect">
                            <select id="simulations" name="simulationList" defaultValue="Select Simulation ID..." onChange={handleSimulationSelection} className={styles.simulationSelector}>
                                <option disabled={true} key="default" className={styles.simSelectionOption}>Select Simulation ID...</option>
                                {simulations && simulations.map((simulation: Simulation) =>
                                    <option key={simulation.simulationId} data-key={simulation.simulationId} className={styles.simSelectionOption}>{simulation.simulationName}</option>)}
                            </select>
                        </div>
                    </form>
                )}
                <CreateNewSimulation formState={formState}
                                     formSwitch={() => {
                                         if (formState === "INIT") {
                                         setFormState("CREATE")
                                         }
                                         if (formState === "CREATE") {
                                         setFormState("INIT")
                                         }
                                     }}
                                    simulations={simulations}
                />
            </div>
        </>
    )
}