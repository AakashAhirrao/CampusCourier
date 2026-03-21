import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

function Login(){

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        console.log("Sending data to Spring Boot...");

        try{
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    },
                body: JSON.stringify({email: email, password: password}),
            });

            if (response.ok) {
                const token = await response.text();

                localStorage.setItem('jwt_token', token);

                console.log("Success!, here is your JWT ID card :", token);

                navigate('/feed');
            }
            else {
                console.log("Login failed!, the bouncer rejected the credentials")
            }
        }
        catch (error){
            console.error("Network connectione error: ", error);
        }
};

    return (
        <div style={{padding: '20px', maxWidth: '400px', margin: '0 auto'}}>
            <h2>Login to CampusCourier 🔒</h2>

            <form onSubmit={handleLogin} style={{display: 'flex', flexDirection: 'column', gap: '15px'}}>

                <input
                    type="email"
                    placeholder = "Email Address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    style={{ padding: '10px' }}
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    style={{ padding: '10px' }}
                />
                <button type="submit" style={{ padding: '10px', backgroundColor: '#007bff', color: 'white', border: 'none', cursor: 'pointer' }}>
                    Login
                </button>
            </form>
            <p style={{ marginTop: '20px', textAlign: 'center' }}>
                    New to CampusCourier? <Link to="/register">Create an account</Link>
                  </p>
        </div>
    );
}

export default Login;