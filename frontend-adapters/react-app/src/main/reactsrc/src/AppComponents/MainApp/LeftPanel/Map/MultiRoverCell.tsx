import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import {Rover} from "../../../../interfaces.ts";
import TooltipLocationInfo from "./TooltipLocationInfo.tsx";
import TooltipRoverName from "./TooltipRoverName.tsx";
import TooltipRoverHeading from "./TooltipRoverHeading.tsx";
import TooltipRoverHealth from "./TooltipRoverHealth.tsx";
import TooltipRoverStatus from "./TooltipRoverStatus.tsx";

interface MultiRoverCellProps {
    rovers: Rover[];
    xCoordinate: number;
    yCoordinate: number;
}

const MultiRoverCell: FC<MultiRoverCellProps> = ({rovers, xCoordinate, yCoordinate}) => {

    return (
        <div className={styles.cell} style={{fontWeight: "bold"}}>
            {rovers.length}
            <span className={styles.tooltiptext} style={{display: "flex", gap: 30}}>
                {rovers.map((rover, roverIndex) => (
                    <div key={roverIndex}>
                        <TooltipRoverName roverName={rover.roverName}/>
                        <TooltipLocationInfo xCoordinate={xCoordinate} yCoordinate={yCoordinate}/>
                        <TooltipRoverHeading roverHeading={rover.roverHeading}/>
                        <TooltipRoverHealth roverHealth={rover.hitPoints}/>
                        <TooltipRoverStatus operationalStatus={rover.operationalStatus}/>
                    </div>
                ))}
            </span>
        </div>
    )
}

export default MultiRoverCell