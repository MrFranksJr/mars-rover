import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import heartIcon from "../../../../assets/icons/heart-solid.svg";

interface RoverHealthProps {
    roverHealth: number
}

const TooltipRoverHealth: FC<RoverHealthProps> = ({roverHealth}) => {
    return (
        <div className={styles.roverStatusInfoBlock}><img src={heartIcon}
                                                          className={styles.healthIcon}
                                                          alt="heart icon"/> {roverHealth}/5
        </div>
    )
}

export default TooltipRoverHealth