import {FC, useContext} from "react";
import {SimulationContext} from "../../../SimulationContext.tsx";
import SingleRoverCell from "./SingleRoverCell.tsx";
import MultiRoverCell from "./MultiRoverCell.tsx";
import EmptyCell from "./EmptyCell.tsx";

interface CellProps {
    numberOfRows: number;
    numberOfColumns: number;
}

const Cell: FC<CellProps> = ({numberOfRows, numberOfColumns}) => {
    const {simulation} = useContext(SimulationContext);

    const array = new Array(simulation.totalCoordinates).fill(0).map((_, index) => index);

    const cells = array.map((index: number) => {
        const xCell = index % numberOfColumns;
        const yCell = numberOfRows - 1 - Math.floor(index / numberOfColumns);
        const roversAtCell = simulation.roverList.filter(rover => rover.roverXPosition === xCell && rover.roverYPosition === yCell);

        return (
            <div key={index} className="item" style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                {roversAtCell.length === 1 &&
                    <SingleRoverCell
                        rover={roversAtCell[0]}
                        xCoordinate={xCell}
                        yCoordinate={yCell}
                    />
                }
                {roversAtCell.length > 1 &&
                    <MultiRoverCell
                        rovers={roversAtCell}
                        xCoordinate={xCell}
                        yCoordinate={yCell}
                    />
                }
                {!roversAtCell[0] &&
                    <EmptyCell
                        xCoordinate={xCell}
                        yCoordinate={yCell}
                    />
                }
            </div>
        )
    });

    return (
        <>
            {cells}
        </>
    )
}

export default Cell