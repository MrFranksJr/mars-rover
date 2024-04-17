//RoverControl.tsx:
import {Rover} from "../../../interfaces.ts";
import styles from "../../../styles/RoverControl.module.css"
import {ChangeEvent, useEffect, useState} from "react";

interface RoverControlProps extends Rover {
    instruction: string;
    onInstructionChange: (roverName: string, instruction: string) => void;
}

function RoverControl({ roverName, operationalStatus, instruction, onInstructionChange }: RoverControlProps) {
    const [inputValue, setInputValue] = useState(instruction);

    useEffect(() => {
        setInputValue(instruction);
    }, [instruction]);

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const instruction = event.target.value;
        setInputValue(instruction);
        onInstructionChange(roverName, instruction);
    };

    return (
        <>
            <div className={styles.singleRoverInstruction}>
                <label className={styles.instructionLabel} htmlFor={roverName}>{roverName}</label>
                <input
                    id={roverName}
                    name={roverName}
                    className={styles.roverInstructions}
                    placeholder={operationalStatus === "OPERATIONAL" ? "Enter move instructions" : "Rover broken"}
                    type="text"
                    value={inputValue}
                    onChange={handleChange}
                    disabled={operationalStatus !== "OPERATIONAL"}
                />
            </div>
        </>
    );
}

export default RoverControl