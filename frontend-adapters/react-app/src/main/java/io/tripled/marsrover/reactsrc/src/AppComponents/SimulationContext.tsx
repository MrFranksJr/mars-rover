import React, {createContext, useState} from "react";
import {Simulation} from "../interfaces.ts";

interface SimulationContextProps {
    simulation: Simulation;
    setSimulation: (simulation: Simulation) => void;
}

const SimulationContext = createContext<SimulationContextProps>({
    simulation: {} as Simulation,
    setSimulation: () => {
    },
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

    return (
        <SimulationContext.Provider value={{simulation, setSimulation: handleSetSimulation}}>
            {children}
        </SimulationContext.Provider>
    );
};

export {SimulationContext, SimulationProvider};