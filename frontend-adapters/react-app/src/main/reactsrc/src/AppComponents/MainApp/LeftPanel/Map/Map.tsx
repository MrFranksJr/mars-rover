import {JSX, useContext, useEffect, useRef, useState} from "react";
import styles from '../../../../styles/Map.module.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";
import {SimulationContext} from "../../../SimulationContext.tsx";

function Map(): JSX.Element {
    const {simulation} = useContext(SimulationContext);
    const [height, setHeight] = useState(0)
    const [width, setWidth] = useState(0)
    const ref = useRef<HTMLDivElement>(null)

    useEffect(() => {
        if (ref.current) {
            setHeight(ref.current.clientHeight);
            setWidth(ref.current.clientWidth)
        }
    }, []);

    useEffect(() => {
        // Ensure height and width are non-zero before calculating scale
        if (height > 0 && width > 0) {
            setMapState({
                scale: (height / (205*simulation.simulationSize)),
                translation: { x: width * 0.2, y: height * 0.02 }
            });
        }
    }, [height, width, simulation]);

    const [mapState, setMapState] = useState({
        scale: 1,
        translation: {x: 0, y: 0 }
    });

    const [translationBoundState, setTranslationBoundState] = useState({
        xMin: 0 - (mapState.scale *400),
        xMax: (mapState.scale * 400),
        yMin: 0 - (mapState.scale *400),
        yMax: (mapState.scale * 35)
    })

    return (
        <>
            {console.log(height)}
            {console.log(width)}
            {console.log(translationBoundState.yMax)}
            <div ref={ref} className={styles.interactiveMapWrapper}>
                <MapInteractionCSS
                    showControls
                    value={mapState}
                    minScale={height / (205*simulation.simulationSize)}
                    translationBounds={translationBoundState}
                    onChange={(value) => {
                        setMapState(value)
                        setTranslationBoundState({
                            xMin: 0 - (mapState.scale *3000),
                            xMax: (mapState.scale * 400),
                            yMin: 0 - (mapState.scale *400),
                            yMax: (mapState.scale * 2000)
                        })
                    }}

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
