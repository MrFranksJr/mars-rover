import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import '././styles/index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import SimulationSelection from "./AppComponents/LandingScreen/SimulationSelection.tsx";
import {SimulationProvider} from "./AppComponents/SimulationContext.tsx";

const router = createBrowserRouter([
    {
        path: '/',
        element: <SimulationSelection/>,
    },
    {
        path: '/app',
        element: <App/>,
    },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <SimulationProvider>
            <RouterProvider router={router}/>
        </SimulationProvider>
    </React.StrictMode>
)
