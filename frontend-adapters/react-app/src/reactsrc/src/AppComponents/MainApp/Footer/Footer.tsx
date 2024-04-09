import styles from '../../../styles/Footer.module.css'
import {useContext, useState} from 'react';
import {SimulationContext} from '../../SimulationContext.tsx';
import tripleDLogo from '../../../assets/TripleD.svg'
import InfoModal from '../Footer/InfoModal.tsx'

function Footer() {
    const {simulation} = useContext(SimulationContext)
    const [isInfoModalOpen, setInfoModalOpen] = useState<boolean>(false);

    const handleOpenNewsletterModal = () => {
        setInfoModalOpen(true);
    };

    const handleCloseNewsletterModal = () => {
        setInfoModalOpen(false);
    };

    return (
        <footer id="footer">
            <div id="infoBtn" className={styles.infoBtn} onClick={handleOpenNewsletterModal}><i className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className={styles.copyright}>
                {'\xA9'}{new Date().getFullYear()}{'\xa0'}<img src={tripleDLogo} className={styles.tripledLogo} alt="triple d logo"/> Mars Rover Association
            </div>
            <div id="simulationId" className={styles.simulationId}>
                <div>Simulation ID</div>
                <div>{simulation.simulationId}</div>
            </div>
            <InfoModal
                isOpen={isInfoModalOpen}
                onClose={handleCloseNewsletterModal}
            />
        </footer>
    )
}

export default Footer;