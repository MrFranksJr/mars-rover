import {JSX, useContext, useEffect, useRef, useState} from "react";
import styles from '../../../../styles/Map.module.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";
import {SimulationContext} from "../../../SimulationContext.tsx";

function Map(): JSX.Element {
    const {simulation} = useContext(SimulationContext);
    const interactiveMapWrapperRef = useRef<HTMLDivElement>(null);
    const trueMapSize = (160 * (simulation.simulationSize + 1)) + (16 * simulation.simulationSize) + 60;
    const [minScalingFactor, setMinScalingFactor] = useState(1);
    const [mapState, setMapState] = useState({
        scale: 1,
        translation: {x: 0, y: 0}
    })

    useEffect(() => {
        const updateDivDimensions = () => {
            if (interactiveMapWrapperRef.current) {
                const rect = interactiveMapWrapperRef.current.getBoundingClientRect();
                const width = rect.width;
                const height = rect.height;
                const minDimension = Math.min(width, height);
                const scaleToSet = minDimension / trueMapSize
                const xPosToSet = (width - trueMapSize * scaleToSet) / 2
                setMinScalingFactor(scaleToSet)
                setMapState({scale: scaleToSet, translation: {x: xPosToSet, y: 0}})
            } else {
                console.warn("Div ref is null.");
            }
        };

        updateDivDimensions()

        // Call the function when everything on the page is loaded
        window.onload = () => {
            updateDivDimensions();
        };

        // Add event listener to handle resizing (optional)
        window.addEventListener('resize', updateDivDimensions);

        // Cleanup function to remove event listener
        return () => {
            window.removeEventListener('resize', updateDivDimensions);
        };
    }, [simulation]); // Empty dependency array ensures this effect runs only once after initial render

    return (
        <div ref={interactiveMapWrapperRef} className={styles.interactiveMapWrapper}>
            <MapInteractionCSS
                showControls={true}
                btnClass={styles.mapBtns}
                maxScale={3}
                minScale={minScalingFactor}
                onChange={(value) => {
                    setMapState(value)
                }}
                value={mapState}
            >
                <div className={styles.mapFlexWrapper}>
                    <Grid/>
                </div>
            </MapInteractionCSS>
        </div>
    );
}

export default Map;