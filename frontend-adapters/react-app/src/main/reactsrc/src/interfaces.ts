export interface Rover {
    roverName: string;
    roverHeading: string;
    roverXPosition: number;
    roverYPosition: number;
    hitPoints: number;
    operationalStatus: string;
}

export interface Simulation {
    simulationId: string;
    simulationName: string;
    simulationSize: number;
    totalCoordinates: number;
    roverList: Array<Rover>;
}

export interface Props {
    simulations: Array<Simulation>;
}

export interface FormStateProps {
    formState: string;
    formSwitch: () => void;
}