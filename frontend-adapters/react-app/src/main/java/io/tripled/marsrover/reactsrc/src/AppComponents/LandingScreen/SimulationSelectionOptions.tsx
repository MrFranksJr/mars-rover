import {Props, Simulation} from "../../interfaces.ts";
import {useNavigate} from "react-router";
import {useContext} from "react";
import {SimulationContext} from "../SimulationContext.tsx";

function SimulationSelectionOptions({simulations}: Props) {
    const navigate = useNavigate();
    const { setSimulationId } = useContext(SimulationContext);
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
            setSimulationId(data.simulationId);
            navigate('/app');
        } catch (error) {
            console.error("Error creating new simulation:", error);
        }
    };

    return (
        <>
            <div className="simulationOptions">
                <form className="simulationSelectionForm">
                    <label htmlFor="simulations">Select an existing Simulation</label>
                    <div className="customSelect">
                        <select id="simulations" name="simulationList" defaultValue="Select Simulation ID...">
                            <option disabled={true}>Select Simulation ID...</option>
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