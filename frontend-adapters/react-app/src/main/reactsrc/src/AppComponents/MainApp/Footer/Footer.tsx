import styles from '../../../styles/Footer.module.css'
import React, {useContext} from 'react';
import {SimulationContext} from '../../SimulationContext.tsx';
import tripleDLogo from '../../../assets/TripleD.svg'
import InfoModal from '../Footer/InfoModal.tsx'

interface FooterProps {
    isOpen: boolean;
    toggleModal: () => void;
}

const Footer: React.FC<FooterProps> = ( { isOpen, toggleModal } ) => {
    const {simulation} = useContext(SimulationContext)

    return (
        <div className={styles.footer} id="footer">
            <div id="infoBtn" className={styles.infoBtn} onClick={toggleModal}><i
                className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className={styles.copyright}>
                {'\xA9'}{new Date().getFullYear()}{'\xa0'}<img src={tripleDLogo} className={styles.tripledLogo}
                                                               alt="triple d logo"/> Mars Rover Association
            </div>
            <div id="simulationId" className={styles.simulationId}>
                <div>Simulation ID</div>
                <div>{simulation.simulationId}</div>
            </div>
            <InfoModal
                isOpen={isOpen}
                onClose={toggleModal}
            />
        </div>
    )
}

export default Footer;