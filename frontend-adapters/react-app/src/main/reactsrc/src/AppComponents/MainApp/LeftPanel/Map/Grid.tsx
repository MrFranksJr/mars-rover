import React, {useContext} from 'react';
import '../../../../styles/Map.css';
import {SimulationContext} from "../../../SimulationContext.tsx";

interface GridProps {
    simulationSize: number;
}

const Grid: React.FC<GridProps> = () => {
    const {simulation} = useContext(SimulationContext);

    console.log(simulation.totalCoordinates)
    const array = new Array(simulation.totalCoordinates).fill(0).map((_, index) => index);

    // Calculate the number of columns and rows based on the square root of the array length
    const numberOfCols = Math.ceil(Math.sqrt(simulation.totalCoordinates));
    const numberOfRows = Math.ceil(simulation.totalCoordinates / numberOfCols);

    // Generate cells based on the calculated number of columns and rows
    const cells = array.map((index: number) => {
        const xCell = index % numberOfCols;
        const yCell = Math.floor(index / numberOfCols);
        const roverAtCell = simulation.roverList.find(rover => rover.roverXPosition === xCell && rover.roverYPosition === yCell);

        return (
            <div key={index} className="item" style={{backgroundColor: roverAtCell ? '#000000' : `#${Math.random().toString(16).substr(-6)}`, opacity:"20", display:"flex", justifyContent:"center", alignItems:"center"}}>
                {roverAtCell &&
                    <div className="rover" style={{fontSize: 40, color:"white", textAlign:"center"}}>
                        {roverAtCell.roverName}
                    </div>
                }
                {!roverAtCell &&
                    <div className="rover" style={{fontSize: 40, color:"white", textAlign:"center"}}>
                        {index}
                    </div>
                }
            </div>
        )
    });

    return (
        <div className="container" style={{
            gridTemplateColumns: `repeat(${numberOfCols}, minmax(10rem, 1fr))`,
            gridTemplateRows: `repeat(${numberOfRows}, minmax(10rem, 1fr))`
        }}>
            {cells}
        </div>
    );
};

export default Grid;