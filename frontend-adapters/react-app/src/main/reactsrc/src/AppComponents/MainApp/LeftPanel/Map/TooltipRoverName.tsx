import {FC} from "react";
import styles from "../../../../styles/Map.module.css";

interface RoverNameProps {
    roverName: string;
}

const TooltipRoverName: FC<RoverNameProps> = ({ roverName }) => {
    return (
        <div className={styles.roverStatusInfoBlock} style={{fontWeight: "bold"}}>{roverName}</div>
    )
}

export default TooltipRoverName