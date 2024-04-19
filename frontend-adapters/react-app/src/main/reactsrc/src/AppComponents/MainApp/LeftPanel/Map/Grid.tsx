import React, {useContext} from 'react';
import styles from '../../../../styles/Map.module.css';
import {SimulationContext} from "../../../SimulationContext.tsx";
import locationArrowIcon from "../../../../assets/icons/location-arrow-solid.svg"
import heartIcon from "../../../../assets/icons/heart-solid.svg"
import brokenIcon from "../../../../assets/icons/circle-xmark-solid2.svg"
import activeIcon from "../../../../assets/icons/circle-check-solid.svg"
import compassIcon from "../../../../assets/icons/compass-solid.svg"

const Grid: React.FC = () => {
    const {simulation} = useContext(SimulationContext);

    const array = new Array(simulation.totalCoordinates).fill(0).map((_, index) => index);

    const numberOfCols = Math.ceil(Math.sqrt(simulation.totalCoordinates));
    const numberOfRows = Math.ceil(simulation.totalCoordinates / numberOfCols);

    const cells = array.map((index: number) => {
        const xCell = index % numberOfCols;
        const yCell = numberOfRows - 1 - Math.floor(index / numberOfCols);
        const roversAtCell = simulation.roverList.filter(rover => rover.roverXPosition === xCell && rover.roverYPosition === yCell);

        return (
            <div key={index} className="item" style={{display:"flex", justifyContent:"center", alignItems:"center"}}>
                {roversAtCell.length === 1 &&
                    <div className={styles.cell} style={{fontWeight: "bold"}}>
                        {roversAtCell[0].roverName}
                        <span className={styles.tooltiptext}>
                            <div className={styles.roverStatusInfoBlock}><img src={locationArrowIcon} className={styles.locationIcon} alt="location icon"/><div style={{textWrap:"nowrap"}}>{xCell} – {yCell}</div></div>
                            <div className={styles.roverStatusInfoBlock}><img src={compassIcon} className={styles.compassIcon} alt="compass icon"/> {roversAtCell[0].roverHeading}</div>
                            <div className={styles.roverStatusInfoBlock}><img src={heartIcon} className={styles.healthIcon} alt="heart icon"/> {roversAtCell[0].hitPoints}/5</div>
                            {roversAtCell[0].operationalStatus === "OPERATIONAL" &&
                                <div className={styles.roverStatusInfoBlock} style={{color:"7ecc2d"}}><img src={activeIcon} className={styles.goodIcon} alt="good status icon"/> {roversAtCell[0].operationalStatus}</div>
                            }
                            {roversAtCell[0].operationalStatus === "BROKEN" &&
                                <div className={styles.roverStatusInfoBlock} style={{color:"f18b82"}}><img src={brokenIcon} className={styles.brokenIcon} alt="bad status icon"/> {roversAtCell[0].operationalStatus}</div>
                            }
                        </span>
                    </div>
                }
                {roversAtCell.length > 1 &&
                    <div className={styles.cell} style={{ fontWeight: "bold" }}>
                        {roversAtCell.length}
                        <span className={styles.tooltiptext} style={{display:"flex", gap:30}}>
                            {roversAtCell.map((rover, roverIndex) => (
                                <div key={roverIndex}>
                                    <div className={styles.roverStatusInfoBlock} style={{fontWeight:"bold"}}>{rover.roverName}</div>
                                    <div className={styles.roverStatusInfoBlock}><img src={locationArrowIcon} className={styles.locationIcon} alt="location icon"/><div style={{textWrap:"nowrap"}}>{xCell} – {yCell}</div></div>
                                    <div className={styles.roverStatusInfoBlock}><img src={compassIcon} className={styles.compassIcon} alt="compass icon"/> {roversAtCell[0].roverHeading}</div>
                                    <div className={styles.roverStatusInfoBlock}><img src={heartIcon} className={styles.healthIcon} alt="heart icon"/> {roversAtCell[0].hitPoints}/5</div>
                                    {roversAtCell[0].operationalStatus === "OPERATIONAL" &&
                                        <div className={styles.roverStatusInfoBlock} style={{color:"7ecc2d"}}><img src={activeIcon} className={styles.goodIcon} alt="good status icon"/> {roversAtCell[0].operationalStatus}</div>
                                    }
                                    {roversAtCell[0].operationalStatus === "BROKEN" &&
                                        <div className={styles.roverStatusInfoBlock} style={{color:"f18b82"}}><img src={brokenIcon} className={styles.brokenIcon} alt="bad status icon"/> {roversAtCell[0].operationalStatus}</div>
                                    }
                                </div>
                            ))}
                        </span>
                    </div>
                }
                {!roversAtCell[0] &&
                    <div className={styles.cell}>

                        <span className={styles.tooltiptext}>
                            <div className={styles.roverStatusInfoBlock}><img src={locationArrowIcon} className={styles.locationIcon} alt="location icon"/><div style={{textWrap: "nowrap"}}>{xCell} – {yCell}</div></div>
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