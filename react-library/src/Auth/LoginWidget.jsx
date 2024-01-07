import { useState, useEffect } from 'react';
import './Login.css';
import { useHistory } from 'react-router-dom';


const LoginWidget = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const history = useHistory(); 

    const handleSubmit = async (event) => {
        event.preventDefault();
        const token = btoa(`${username}:${password}`);
        
        try {
            const response = await fetch('http://127.0.0.1:8082/api/login', {
                method: 'GET',
                headers: { 
                    "Authorization": "Basic " + token
                },
            });
    
            if (response.ok) {
                localStorage.setItem('basicAuth', token);
                // Make another request to get user details (assuming such an endpoint exists)
                const userDetailsResponse = await fetch('http://127.0.0.1:8082/api/userDetails', {
                    headers: { "Authorization": "Basic " + token }
                });
    
                if (userDetailsResponse.ok) {
                    const userDetails = await userDetailsResponse.json();
                    console.log("Authenticated User:", userDetails.username);
                    console.log("User Role:", userDetails.role);
                    localStorage.setItem('user', JSON.stringify(userDetails));
                    onLogin(userDetails.role);
                    history.push('/');
                } else {
                    console.log("Failed to retrieve user details.");
                }
            } else {
                console.log("Login failed.");
                setError("Login failed. Please check your credentials.");
            }
        } catch (error) {
            setError('An error occurred. Please try again later.');
            console.error('Error:', error);
        }
    };
    

    return (
        <div className="login-container">
            <form onSubmit={handleSubmit} className="login-form">
                <p className="text-center">Se connecter</p>
                <div className="form-group">
                    <label htmlFor="username">Nom d'utilisateur</label>
                    <input type="text" id="username" required value={username}
                           onChange={(e) => setUsername(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Mot de passe</label>
                    <input type="password" id="password" required value={password}
                           onChange={(e) => setPassword(e.target.value)} />
                </div>
                <div className="form-group">
                    <input type="checkbox" id="remember-me" />
                    <label htmlFor="remember-me">Se souvenir de moi</label>
                </div>
                <button type="submit" className="login-button">Se connecter</button>
            </form>
        </div>
    );
};

export default LoginWidget;
