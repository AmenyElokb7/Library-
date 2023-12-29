// YourComponent.jsx
import React from 'react';
import { useAuth } from '../../Auth/AuthContext';

const AuthService = () => {
  const { authenticated, login, logout } = useAuth();

  const handleLogin = () => {
    // Implement your login logic here
    login();
  };

  const handleLogout = () => {
    // Implement your logout logic here
    logout();
  };

  return (
    <div>
      {authenticated ? (
        <>
          <p>Welcome, User!</p>
          <button onClick={handleLogout}>Logout</button>
        </>
      ) : (
        <>
          <p>Please log in.</p>
          <button onClick={handleLogin}>Login</button>
        </>
      )}
    </div>
  );
};

export default AuthService;
