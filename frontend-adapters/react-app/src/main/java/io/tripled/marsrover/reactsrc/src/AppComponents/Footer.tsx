import '../Styles/Footer.css'
import {useEffect} from "react";

function Footer() {
    useEffect(() => {
        fetch('/api/simulationstates')
            .then(response => response.text())
            .then(data => console.log(data));
    }, []);

    return (
        <footer id="footer">
            <div id="infoBtn" className="infoBtn"><i className="fa-solid fa-circle-info fa-2xl"></i></div>
            <div id="copyright" className="copyright"></div>
            <div id="simulationId" className="simulationId">
                <label htmlFor="simulations">Simulation ID</label>
                <select id="simulations" name="simulationList">
                    <option></option>
                </select>
            </div>
        </footer>
    )
}

export default Footer;