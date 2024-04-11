import React, {createContext, useState} from "react";
import {Simulation} from "../interfaces.ts";

interface SimulationContextProps {
    simulation: Simulation;
    setSimulation: (simulation: Simulation) => void;
    updateSimulation: (simulationId: string) => Promise<void>;
}

const SimulationContext = createContext<SimulationContextProps>({
    simulation: {} as Simulation,
    setSimulation: () => {},
    updateSimulation: async () => {} // Add a new function to update simulation data
});

const SimulationProvider = ({children}: { children: React.ReactNode }) => {
    const [simulation, setSimulation] = useState<Simulation>({
        roverList: [],
        simulationId: "",
        simulationSize: 0,
        totalCoordinates: 0
    });

    const handleSetSimulation = (simulation: Simulation) => {
        setSimulation(simulation);
    };

    const updateSimulation = async (simulationId: string) => {
        try {
            const response = await fetch(`/api/simulationstate/${simulationId}`, {
                headers: {'Content-Type': 'application/json'}
            });
            if (!response.ok) {
                console.error(`Error getting simulationstate for SimulationId ${simulationId}`)
                throw new Error(`Failed to get simulation state: ${response.statusText}`);
            }
            const data = await response.json();
            setSimulation({
                simulationId: data.simulationId,
                simulationSize: data.simulationSize,
                totalCoordinates: data.totalCoordinates,
                roverList: data.roverList
            });
        } catch (error) {
            console.error("Error updating simulation state:", error);
            throw error;
        }
    };

    return (
        <SimulationContext.Provider value={{simulation, setSimulation: handleSetSimulation, updateSimulation}}>
            {children}
        </SimulationContext.Provider>
    );
};

export {SimulationContext, SimulationProvider};