import Header from "./AppComponents/MainApp/Header.tsx";
import Footer from "./AppComponents/MainApp/Footer/Footer.tsx";
import RightPanel from "./AppComponents/MainApp/RightPanel/RightPanel.tsx";
import LeftPanel from "./AppComponents/MainApp/LeftPanel/LeftPanel.tsx";
import styles from "./styles/App.module.css"
import {useState} from "react";

function App() {
    const [isInfoModalOpen, setInfoModalOpen] = useState(false);

    const handleToggleModal = () => {
        setInfoModalOpen(prevState => !prevState);
    };

    return (
        <>
            <Header/>
            <div className={`${styles.mainPanels} ${isInfoModalOpen ? styles.modalOpen : ''}`} id="main-panels">
                <LeftPanel />
                <RightPanel />
            </div>
            <Footer
                isOpen={isInfoModalOpen}
                toggleModal={handleToggleModal}
            />
        </>
    );
}


export default App
