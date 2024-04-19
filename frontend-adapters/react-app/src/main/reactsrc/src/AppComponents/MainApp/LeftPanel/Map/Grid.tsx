import React, {useContext} from 'react';
import styles from '../../../../styles/Map.module.css';
import {SimulationContext} from "../../../SimulationContext.tsx";
import Cell from "./Cell.tsx";

const Grid: React.FC = () => {
    const {simulation} = useContext(SimulationContext);

    const numberOfCols = Math.ceil(Math.sqrt(simulation.totalCoordinates));
    const numberOfRows = Math.ceil(simulation.totalCoordinates / numberOfCols);

    return (
        <div className={styles.container} style={{
            gridTemplateColumns: `repeat(${numberOfCols}, minmax(10rem, 1fr))`,
            gridTemplateRows: `repeat(${numberOfRows}, minmax(10rem, 1fr))`
            }}>
            <Cell numberOfColumns={numberOfCols} numberOfRows={numberOfRows}/>
        </div>
    );
};

export default Grid;