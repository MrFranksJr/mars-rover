import './App.css'
import Header from "./AppComponents/Header.tsx";
import Footer from "./AppComponents/Footer.tsx";
import RightPanel from "./AppComponents/RightPanel.tsx";
import LeftPanel from "./AppComponents/LeftPanel.tsx";

function App() {
    return (
        <>
            <Header/>
            <div className="main-panels" id="main-panels">
                <LeftPanel/>
                <RightPanel/>
            </div>
            <Footer/>
        </>
    )
}

export default App
