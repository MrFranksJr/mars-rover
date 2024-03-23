import {Props, Simulation} from "../../interfaces.ts";

function SimulationSelectionOptions({simulations}: Props) {

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
                    <button>Create...</button>
                </div>
            </div>
        </>
    )
}

export {SimulationSelectionOptions}