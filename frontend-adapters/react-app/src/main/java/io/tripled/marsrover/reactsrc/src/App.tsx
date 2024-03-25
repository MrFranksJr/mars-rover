import '././styles/App.css'
import Header from "./AppComponents/MainApp/Header.tsx";
import Footer from "./AppComponents/MainApp/Footer.tsx";
import RightPanel from "./AppComponents/MainApp/RightPanel/RightPanel.tsx";
import LeftPanel from "./AppComponents/MainApp/LeftPanel/LeftPanel.tsx";

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
    );
}


export default App
