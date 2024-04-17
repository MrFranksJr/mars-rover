import React from 'react';
import Modal from '../UtilComponents/Modal';
import styles from '../../../styles/infoModal.module.css'

interface InfoModalProps {
    isOpen: boolean;
    onClose: () => void;
}

const InfoModal: React.FC<InfoModalProps> = ({
                                           isOpen,
                                           onClose,
                                       }) => {
    return (
        <Modal isOpen={isOpen}
               hasCloseBtn={true}
               onClose={onClose}
        >
            <h2 className={styles.h2}>Using the Mars Rover Simulator</h2>
            <p className={styles.p}>The application has 4 main sections to take into account:
            </p>
                <ul className={styles.ul}>
                    <li>The <strong>Landing Controls</strong> in the top-left</li>
                    <li>The <strong>Rover Instructions</strong> section in the top-right</li>
                    <li>An overview of the <strong>Simulation State</strong> at the bottom-right</li>
                    <li>The <strong>Simulation Map</strong> makes up the bottom-left section of the screen</li>
                </ul>
            <p className={styles.p}>
                In the <strong>Landing Controls</strong> section you have the ability to land new Rovers onto the
                Simulation.
                Simply enter a X and Y coordinate (within the Simulation limits) and press the Landing button or simply
                hit
                Return on your keyboard.
            </p>
            <p className={styles.p}>
                In the <strong>Rover Instructions controls</strong> you can give instructions to any of the Rovers
                currently in
                the field.
            </p>
                <ul className={styles.ul}>
                    <li>You'll need to instruct the rovers with the characters <em>"F", "R", "B", "L"</em> to make the
                        Rovers move
                        Forward, Right, Backward or Left.
                    </li>
                    <li>Multipliers can also be applied here, meaning if you want to make the Rover move 5 steps
                        forward, you would
                        enter <em>"f5"</em> in the Instruction Field. Turning the Rover left 3 times would correspond
                        with the
                        command <em>"l3"</em>.
                    </li>
                    <li>Instructions can also be chained together, meaning if I want to make a Rover move 5 steps
                        Forward, turn Left
                        and then move Forward another 5 steps, the Instruction entered would look like this: <em>"F5 L
                            F5"</em>.
                    </li>
                </ul>
            <p className={styles.p}>
                The <strong>Simulation State</strong> section will give you all the available information with regards
                to the
                current Simulation.
            </p>
            <p className={styles.p}>
                The <strong>Simulation Map</strong> will give you a visual representation of the current state of the
                Simulation. Rovers currently on the simulation will be represented by a symbol such as this
                <strong>∧R1</strong>
            </p>
                <ul className={styles.ul}>
                    <li>The symbol representing the Rover will show as its ID (eg. "R1"). The symbol also includes an
                        indicator of
                        the the current heading of that rover: <strong>∧</strong> for North, <strong>›</strong> for
                        East, <strong>∨</strong> for South and <strong>‹</strong> for West.
                    </li>
                    <li>Hover over the Rovers with your mouse to receive additional information about that Rover</li>
                    <li>Should a Rover break, it becomes immobile. This will also be visible by the red color, as well
                        as this
                        symbol: <strong>⊗</strong>
                    </li>
                </ul>
        </Modal>
    )
}

export default InfoModal