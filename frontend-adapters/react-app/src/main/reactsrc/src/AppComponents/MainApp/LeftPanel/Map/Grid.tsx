import React from 'react';
import '../../../../styles/Map.css';

interface GridProps {
    arrOfObj: number[];
}

const Grid: React.FC<GridProps> = (props) => {
    const { arrOfObj } = props;

    // Calculate the number of columns and rows based on the square root of the array length
    const numOfCols = Math.ceil(Math.sqrt(arrOfObj.length));
    const numOfRows = Math.ceil(arrOfObj.length / numOfCols);

    // Generate cells based on the calculated number of columns and rows
    const cells = arrOfObj.map((obj: number) => (
        <div key={obj} style={{ backgroundColor: `#${Math.random().toString(16).substr(-6)}`}} className="item">{obj}</div>
    ));

    return (
        <div className="container" style={{ gridTemplateColumns: `repeat(${numOfCols}, minmax(10rem, 1fr))`, gridTemplateRows: `repeat(${numOfRows}, minmax(10rem, 1fr))` }}>
            {cells}
        </div>
    );
};

export default Grid;