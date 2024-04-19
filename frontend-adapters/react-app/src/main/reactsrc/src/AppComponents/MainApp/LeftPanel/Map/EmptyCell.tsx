import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import TooltipLocationInfo from "./TooltipLocationInfo.tsx";

interface EmptyCellProps {
    xCoordinate: number;
    yCoordinate: number;
}

const EmptyCell: FC<EmptyCellProps> = ({xCoordinate, yCoordinate}) => {
    return (
        <div className={styles.cell}>
                        <span className={styles.tooltiptext}>
                            <TooltipLocationInfo xCoordinate={xCoordinate} yCoordinate={yCoordinate}/>
                        </span>
        </div>
    )
}

export default EmptyCell