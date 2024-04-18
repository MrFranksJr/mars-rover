import React, {useContext} from 'react';
import '../../../../styles/Map.css';
import {SimulationContext} from "../../../SimulationContext.tsx";

const Grid: React.FC = () => {
    const {simulation} = useContext(SimulationContext);

    const array = new Array(simulation.totalCoordinates).fill(0).map((_, index) => index);

    const numberOfCols = Math.ceil(Math.sqrt(simulation.totalCoordinates));
    const numberOfRows = Math.ceil(simulation.totalCoordinates / numberOfCols);

    const cells = array.map((index: number) => {
        const xCell = index % numberOfCols;
        const yCell = Math.floor(index / numberOfCols);
        const roverAtCell = simulation.roverList.find(rover => rover.roverXPosition === xCell && rover.roverYPosition === yCell);

        return (
            <div key={index} className="item" style={{display:"flex", justifyContent:"center", alignItems:"center", borderRadius:20}}>
                {roverAtCell &&
                    <div className="rover" style={{fontSize: 40, color:"black", textAlign:"center", fontWeight:"bold"}}>
                        {roverAtCell.roverName}
                    </div>
                }
                {!roverAtCell &&
                    <div className="rover" style={{fontSize: 40, color:"gray", textAlign:"center"}}>
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