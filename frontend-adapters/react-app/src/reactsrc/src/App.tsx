import Header from "./AppComponents/MainApp/Header.tsx";
import Footer from "./AppComponents/MainApp/Footer/Footer.tsx";
import RightPanel from "./AppComponents/MainApp/RightPanel/RightPanel.tsx";
import LeftPanel from "./AppComponents/MainApp/LeftPanel/LeftPanel.tsx";
import styles from "./styles/App.module.css"

function App() {
    return (
        <>
            <Header/>
            <div className={styles.mainPanels} id="main-panels">
                <LeftPanel />
                <RightPanel />
            </div>
            <Footer/>
        </>
    );
}


export default App
