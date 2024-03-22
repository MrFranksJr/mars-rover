import {Props, Simulation} from "./interfaces.ts";

function SimulationSelection({ simulations }: Props) {
    return (
    <>
        <div className="simulationSelection">
            <label htmlFor="simulations">Simulation ID</label>
            <select id="simulations" name="simulationList">
                <option key="newSim">Create New Simulation...</option>
                {simulations && simulations.map((simulation: Simulation) =>
                    <option key={simulation.simulationId}>{simulation.simulationId}</option>)}
            </select>
        </div>
    </>
    )
}