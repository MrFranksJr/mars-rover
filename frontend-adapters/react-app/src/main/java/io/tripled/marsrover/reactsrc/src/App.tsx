import '././styles/App.css'
import Header from "./AppComponents/Header.tsx";
import Footer from "./AppComponents/Footer.tsx";
import RightPanel from "./AppComponents/RightPanel.tsx";
import LeftPanel from "./AppComponents/LeftPanel.tsx";
import {useLocation} from "react-router";

function App() {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const simulationId = searchParams.get('simulationId');

    return (
        <>
            <Header/>
            <div className="main-panels" id="main-panels">
                <LeftPanel/>
                <RightPanel/>
            </div>
            <Footer simulationId={simulationId}/>
        </>
    );
}


export default App
