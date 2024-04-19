import {FC} from "react";
import styles from "../../../../styles/Map.module.css";
import activeIcon from "../../../../assets/icons/circle-check-solid.svg";
import brokenIcon from "../../../../assets/icons/circle-xmark-solid2.svg";

interface RoverStatusProps {
    operationalStatus: string;
}

const TooltipRoverStatus: FC<RoverStatusProps> = ({operationalStatus}) => {
    return (
        <>
            {operationalStatus === "OPERATIONAL" &&
                <div className={styles.roverStatusInfoBlock} style={{color: "7ecc2d"}}>
                    <img src={activeIcon} className={styles.goodIcon} alt="good status icon"/>
                    {operationalStatus}
                </div>
            }
            {operationalStatus === "BROKEN" &&
                <div className={styles.roverStatusInfoBlock} style={{color: "f18b82"}}>
                    <img src={brokenIcon} className={styles.brokenIcon} alt="bad status icon"/>
                    {operationalStatus}
                </div>
            }
        </>
    )
}

export default TooltipRoverStatus