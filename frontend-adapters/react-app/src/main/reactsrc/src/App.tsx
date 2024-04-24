//app.tsx
import Header from "./AppComponents/MainApp/Header.tsx";
import Footer from "./AppComponents/MainApp/Footer/Footer.tsx";
import RightPanel from "./AppComponents/MainApp/RightPanel/RightPanel.tsx";
import LeftPanel from "./AppComponents/MainApp/LeftPanel/LeftPanel.tsx";
import styles from "./styles/App.module.css"
import {useContext, useEffect, useState} from "react";
import {SimulationContext} from "./AppComponents/SimulationContext.tsx";
import {useNavigate} from "react-router";

function App() {
    const [isInfoModalOpen, setInfoModalOpen] = useState(false);
    const {updateSimulation} = useContext(SimulationContext);
    const navigate = useNavigate();

    useEffect(() => {
        const savedSimulationId = localStorage.getItem('simulationId');
        const currentPath = window.location.pathname; // Get the current URL path
        if (currentPath === '/app') {
            if (savedSimulationId) {
                // If there's a saved simulation ID, update the simulation
                updateSimulation(JSON.parse(savedSimulationId));
            } else {
                navigate("/")
            }
        } else if (currentPath !== '/app') {
            // If the current path is not '/app', navigate to '/app'
            navigate("/");
        }
    }, [navigate]);

    const handleToggleModal = () => {
        setInfoModalOpen(prevState => !prevState);
    };

    return (
        <>
            <Header/>
            <div className={`${styles.mainPanels} ${isInfoModalOpen ? styles.modalOpen : ''}`} id="main-panels">
                <LeftPanel/>
                <RightPanel/>
            </div>
            <Footer
                isOpen={isInfoModalOpen}
                toggleModal={handleToggleModal}
            />
        </>
    );
}


export default App
