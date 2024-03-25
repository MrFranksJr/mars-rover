import {Props, Rover, Simulation} from "../../interfaces.ts";
import {useNavigate} from "react-router";
import {ChangeEvent, useContext} from "react";
import {SimulationContext} from "../SimulationContext.tsx";

function SimulationSelectionOptions({simulations}: Props) {
    const navigate = useNavigate();
    let simulationId: string = "";
    const { setSimulation } = useContext(SimulationContext);
    const handleCreateSimulation = async () => {
        try {
            const response = await fetch('/api/createsimulation/10', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });
            if (!response.ok) {
                throw new Error(`Failed to get simulation ID: ${response.statusText}`);
            }
            const data = await response.json();
            simulationId = data.simulationId;
            setSimulation(getSimulation(simulationId))
            navigate('/app');
        } catch (error) {
            console.error("Error creating new simulation:", error);
        }
    };

    const handleSimulationSelection = (e : ChangeEvent<HTMLSelectElement>) => {
        simulationId = e.target.value;
        setSimulation(getSimulation(simulationId))
        navigate('/app');
    };


    const getSimulation = async () => {
        try {
            console.log(`/api/simulationstate/${simulationId}`)
            const response = await fetch(`/api/simulationstate/${simulationId}`, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            });
            if (!response.ok) {
                throw new Error(`Failed to get simulation ID: ${response.statusText}`);
            }
            const data = await response.json();
            return new class implements Simulation {
                roverList: Array<Rover> = data.RoverList;
                simulationId: string = data.simulationId;
                simulationSize: number = data.simulationSize;
                totalCoordinates: number = data.totalCoordinates;
            }
        } catch (error) {
            console.error("Error creating new simulation:", error);
        }
    }

    return (
        <>
            <div className="simulationOptions">
                <form className="simulationSelectionForm">
                    <label htmlFor="simulations">Select an existing Simulation</label>
                    <div className="customSelect">
                        <select id="simulations" name="simulationList" defaultValue="Select Simulation ID..." onChange={handleSimulationSelection}>
                            <option disabled={true} key="default">Select Simulation ID...</option>
                            {simulations && simulations.map((simulation: Simulation) =>
                                <option key={simulation.simulationId}>{simulation.simulationId}</option>)}
                        </select>
                    </div>
                </form>
                <div className="newSimulation">
                    <label>Create a new Simulation</label>
                    <button onClick={handleCreateSimulation}>Create...</button>
                </div>
            </div>
        </>
    )
}

export {SimulationSelectionOptions}