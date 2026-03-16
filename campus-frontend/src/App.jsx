import { Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Feed from './pages/Feed'
import './App.css'

function App() {
    return (
        <div>
            <h1>CampusCourier 🚀</h1>
            <Routes>
                <Route path="/" element={<Login />} />

                <Route path="/feed" element={<Feed />} />
            </Routes>
        </div>
        )
    }

export default App