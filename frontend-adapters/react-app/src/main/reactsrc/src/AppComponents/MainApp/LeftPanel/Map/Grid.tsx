import React, {useContext} from 'react';
import styles from '../../../../styles/Map.module.css';
import {SimulationContext} from "../../../SimulationContext.tsx";
import locationArrowIcon from "../../../../assets/icons/location-arrow-solid.svg"
import heartIcon from "../../../../assets/icons/heart-solid.svg"
import brokenIcon from "../../../../assets/icons/heart-circle-xmark-solid.svg"
import activeIcon from "../../../../assets/icons/heart-circle-check-solid.svg"
import compassIcon from "../../../../assets/icons/compass-solid.svg"

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
            <div key={index} className="item" style={{display:"flex", justifyContent:"center", alignItems:"center"}}>
                {roverAtCell &&
                    <div className={styles.cell} style={{color: "yellow", fontWeight: "bold"}}>
                        {roverAtCell.roverName}
                        <span className={styles.tooltiptext}>
                            <div className={styles.roverStatusInfoBlock}><img src={locationArrowIcon} className={styles.mapIcon}/><div style={{textWrap:"nowrap"}}>{xCell} : {yCell}</div></div>
                            <div className={styles.roverStatusInfoBlock}><img src={heartIcon} className={styles.mapIcon}/> {roverAtCell.hitPoints}/5</div>
                            <div className={styles.roverStatusInfoBlock}><img src={compassIcon} className={styles.mapIcon}/> {roverAtCell.roverHeading}</div>
                            <div className={styles.roverStatusInfoBlock}><img src={activeIcon} className={styles.mapIcon}/> {roverAtCell.operationalStatus}</div>
                        </span>
                    </div>
                }
                {!roverAtCell &&
                    <div className={styles.cell}>

                        <span className={styles.tooltiptext}>
                            <div className={styles.roverStatusInfoBlock}><img src={locationArrowIcon} className={styles.mapIcon}/><div style={{textWrap:"nowrap"}}>{xCell} : {yCell}</div></div>
                        </span>
                    </div>
                }
            </div>
        )
    });

    return (
        <div className={styles.container} style={{
            gridTemplateColumns: `repeat(${numberOfCols}, minmax(10rem, 1fr))`,
            gridTemplateRows: `repeat(${numberOfRows}, minmax(10rem, 1fr))`
        }}>
            {cells}
        </div>
    );
};

export default Grid;