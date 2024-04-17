import landingControlsStyles from "../../../styles/LandingControls.module.css"
import appStyles from "../../../styles/App.module.css"
import {ChangeEvent, FormEvent, useContext, useState} from "react";
import {SimulationContext} from "../../SimulationContext.tsx";

interface CoordinateData {
    roverXCoordinate: string;
    roverYCoordinate: string;
}

function landingControls() {
    const {simulation, updateSimulation} = useContext(SimulationContext)

    const [formData, setFormData] = useState<CoordinateData>({
        roverXCoordinate: "",
        roverYCoordinate: "",
    });

    const [errors, setErrors] = useState<{ roverXCoordinate?: string; roverYCoordinate?: string }>({});

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {id, value} = event.target;
        setFormData((prevState) => ({...prevState, [id]: value}));
    };

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!formData.roverXCoordinate || !formData.roverYCoordinate) {
            setErrors({
                roverXCoordinate: !formData.roverXCoordinate ? "X Coordinate is required" : "",
                roverYCoordinate: !formData.roverYCoordinate ? "Y Coordinate is required" : "",
            });
            return;
        }
        setErrors({});

        try {
            const result = await fetch(`/api/landrover/${simulation.simulationId}/${formData.roverXCoordinate}/${formData.roverYCoordinate}`, {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json; charset=UTF-8',
                },
            });

            if (!result.ok) {
                throw new Error('Failed to land rover. Please try again later.');
            } else {
                await updateSimulation(simulation.simulationId);
                setFormData({
                    roverXCoordinate: "",
                    roverYCoordinate: "",
                });
            }

        } catch (error) {
            console.error(error);
            console.error(errors);
            // set error modal?
        }
    };


    return (
        <>
            <form id="landRoverControls" className={landingControlsStyles.landRoverControls} onSubmit={handleSubmit}>
                <div className={landingControlsStyles.coordinateInputFields}>
                    <input id="roverXCoordinate"
                           className={landingControlsStyles.roverXCoordinate}
                           placeholder="Enter X Coordinate"
                           type="number"
                           min="0" max="10"
                           value={formData.roverXCoordinate}
                           onChange={handleChange}
                           required
                    />
                    <input id="roverYCoordinate"
                           className={landingControlsStyles.roverYCoordinate}
                           placeholder="Enter Y Coordinate"
                           type="number"
                           min="0" max="10"
                           value={formData.roverYCoordinate}
                           onChange={handleChange}
                           required
                    />
                </div>
                <button id="landRoverBtn" className={appStyles.landRoverBtn}>Land new Rover</button>
            </form>
        </>
    )
}

export default landingControls