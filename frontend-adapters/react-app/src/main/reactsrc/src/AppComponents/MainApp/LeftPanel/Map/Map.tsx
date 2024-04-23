import {JSX, useContext, useEffect, useRef, useState} from "react";
import styles from '../../../../styles/Map.module.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";
import {SimulationContext} from "../../../SimulationContext.tsx";

function Map(): JSX.Element {
    const {simulation} = useContext(SimulationContext);
    const trueMapSize = (160 * (simulation.simulationSize + 1)) + (16 * simulation.simulationSize) + 60
    const [scalingFactor, setScalingFactor] = useState(1);
    const interactiveMapWrapperRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const calculateScalingFactor = () => {
            if (!interactiveMapWrapperRef.current) return 1;
            const {clientWidth, clientHeight} = interactiveMapWrapperRef.current;
            const minDimension = Math.min(clientWidth, clientHeight);
            return minDimension / trueMapSize;
        };

        const updateScalingFactor = () => {
            setScalingFactor(calculateScalingFactor());
        };

        updateScalingFactor();

        const handleResize = () => {
            updateScalingFactor();
        };
        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, [simulation, trueMapSize]);


    return (
        <>
            <div ref={interactiveMapWrapperRef} className={styles.interactiveMapWrapper}>
                <MapInteractionCSS
                    showControls
                    minScale={scalingFactor}
                    maxScale={1}
                    onChange={()=>{}}
                >
                    <div className={styles.mapFlexWrapper}>
                        <Grid/>
                    </div>
                </MapInteractionCSS>
            </div>
        </>
    )
}

export default Map
