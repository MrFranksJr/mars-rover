// CreateSimulationContext.tsx
import React, { createContext, useState } from "react";

interface SimulationContextProps {
    simulationId?: string;
    setSimulationId: (id: string) => void;
}

const SimulationContext = createContext<SimulationContextProps>({
    simulationId: undefined,
    setSimulationId: () => {},
});

const SimulationProvider = ({ children }: { children: React.ReactNode }) => {
    const [simulationId, setSimulationId] = useState<string | undefined>(undefined);

    const handleSetSimulationId = (id: string) => {
        setSimulationId(id);
    };

    return (
        <SimulationContext.Provider value={{ simulationId, setSimulationId: handleSetSimulationId }}>
            {children}
        </SimulationContext.Provider>
    );
};

export { SimulationContext, SimulationProvider };