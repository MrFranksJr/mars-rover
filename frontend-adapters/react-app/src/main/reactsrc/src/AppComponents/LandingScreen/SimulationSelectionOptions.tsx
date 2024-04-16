import {Props, Simulation} from "../../interfaces.ts";
import {useNavigate} from "react-router";
import {ChangeEvent, useContext} from "react";
import {SimulationContext} from "../SimulationContext.tsx";
import styles from '../../styles/SimulationSelectionOptions.module.css'

function SimulationSelectionOptions({simulations}: Props) {
    const navigate = useNavigate();
    let simulationId: string = "";
    let simulationName: string = "";
    const {setSimulation} = useContext(SimulationContext);
    const handleCreateSimulation = async () => {
        try {
            const response = await fetch('/api/createsimulation/10', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            });
            if (!response.ok) {
                throw new Error(`Failed to get simulation ID: ${response.statusText}`);
            }
            const data = await response.json();
            simulationId = data.simulationId;
            const simulationData = await getSimulation()
            setSimulation(simulationData)
            navigate('/app');
        } catch (error) {
            console.error("Error creating new simulation:", error);
        }
    };

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
    };


    const getSimulation = async (): Promise<Simulation> => {
        try {
            const response = await fetch(`/api/simulationstate/${simulationId}`, {
                headers: {'Content-Type': 'application/json'}
            });
            if (!response.ok) {
                console.error(`Error getting simulationstate for SimulationId ${simulationId}`)
                throw new Error(`Failed to get simulation state: ${response.statusText}`);
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

    return (
        <>
            <div className={styles.simulationOptions}>
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
                <div className={styles.newSimulation}>
                    <label className={styles.labelStyle}>Create a new Simulation</label>
                    <button onClick={handleCreateSimulation} className={styles.createSimulationBtn}>Create...</button>
                </div>
            </div>
        </>
    )
}

export {SimulationSelectionOptions}