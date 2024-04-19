import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import compassIcon from "../../../../assets/icons/compass-solid.svg";

interface RoverHeadingProps {
    roverHeading: string;
}

const TooltipRoverHeading: FC<RoverHeadingProps> = ({roverHeading}) => {
    return (
        <div className={styles.roverStatusInfoBlock}><img src={compassIcon}
                                                          className={styles.compassIcon}
                                                          alt="compass icon"/> {roverHeading}
        </div>
    )
}

export default TooltipRoverHeading