import {JSX} from "react";
//import {SimulationContext} from "../../../SimulationContext.tsx";
import '../../../../styles/Map.css';
import {MapInteractionCSS} from 'react-map-interaction';
import Grid from "./Grid.tsx";

function Map(): JSX.Element {
    //const {simulation} = useContext(SimulationContext);
    /*const backgrounds = [
        marsMap1,
        marsMap2,
        marsMap3,
        marsMap4,
        marsMap5,
        marsMap6,
        marsMap7
    ]

    const generateRandomNumber = (): number => {
        return Math.floor(Math.random() * 7) + 1; // Generates a random number between 1 and 7
    };

    const randomBackgroundPicker = () => {
        return backgrounds[generateRandomNumber()]
    }*/

    const arrOfObj = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120]

    return (
        <>
            <MapInteractionCSS>
                <div className="mapFlexWrapper">
                    <Grid arrOfObj={arrOfObj} />
                </div>
            </MapInteractionCSS>
        </>
    )
}

export default Map
