export interface SimulationId {
    simulationId: string|null;
}

export interface Rover {
    roverName: string;
    roverHeading: string;
    xPosition: number;
    yPosition: number;
    hitPoints: number;
    status: string;
}

export interface Simulation {
    simulationId: string;
    simulationSize: number;
    totalCoordinates: number;
    roverlist: Array<Rover>;
}

export interface Props {
    simulations: Array<Simulation>;
}