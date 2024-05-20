import './App.css';

import {createTheme, Switch, ThemeProvider} from "@mui/material";
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Home from "./pages/Home";
import Anime from "./components/Anime";

function App() {
    const theme = createTheme(
    {
        palette: {
            primary: {
                main: '#880e4f',
            },
            secondary: {
                main: '#311b92',
            },
        },
    });
    return (
        <ThemeProvider theme={theme}>
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route index element ={<Home />}/>
                    <Route path="/home" element={<Home />}/>
                    <Route path="/animes" element={<Anime />}/>
                </Routes>
            </BrowserRouter>
        </div>
        </ThemeProvider>
    );
}

export default App;
