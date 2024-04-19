import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import {Rover} from "../../../../interfaces.ts";
import TooltipLocationInfo from "./TooltipLocationInfo.tsx";
import TooltipRoverHeading from "./TooltipRoverHeading.tsx";
import TooltipRoverHealth from "./TooltipRoverHealth.tsx";
import TooltipRoverStatus from "./TooltipRoverStatus.tsx";

interface SingleRoverCellProps {
    rover: Rover;
    xCoordinate: number;
    yCoordinate: number;
}

const SingleRoverCell: FC<SingleRoverCellProps> = ({rover, xCoordinate, yCoordinate}) => {
    return (
        <div className={styles.cell} style={{fontWeight: "bold"}}>
            {rover.roverName}
            <span className={styles.tooltiptext}>
                <TooltipLocationInfo xCoordinate={xCoordinate} yCoordinate={yCoordinate}/>
                <TooltipRoverHeading roverHeading={rover.roverHeading}/>
                <TooltipRoverHealth roverHealth={rover.hitPoints}/>
                <TooltipRoverStatus operationalStatus={rover.operationalStatus}/>
            </span>
        </div>
    )
}

export default SingleRoverCell