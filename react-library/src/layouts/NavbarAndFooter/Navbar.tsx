import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import { SpinnerLoading } from '../Utils/SpinnerLoading';
import { useAuth } from '../../AuthContext';

export const Navbar = () => {
  const { authState, login, logout } = useAuth();

  if (!authState) {
    return <SpinnerLoading />;
  }

  const handleLogout = async () => {
    logout();
    // If you want to redirect after logout, you can use history.push('/your-redirect-path')
  };

  const handleStaticLogin = () => {
    // Implement your static login logic here
    login('user', '1234'); // Sample login for user
    // or login('admin', '1234'); // Sample login for admin
  };

  return (
    <nav className='navbar navbar-expand-lg navbar-dark fixed-top' style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)', backdropFilter: 'blur(5px)' }}>
      <div className='container'>
        <span className='navbar-brand'>Library</span>
        <button
          className='navbar-toggler'
          type='button'
          data-bs-toggle='collapse'
          data-bs-target='#navbarNavDropdown'
          aria-controls='navbarNavDropdown'
          aria-expanded='false'
          aria-label='Toggle Navigation'
        >
          <span className='navbar-toggler-icon'></span>
        </button>
        <div className='collapse navbar-collapse' id='navbarNavDropdown'>
          <ul className='navbar-nav me-auto mb-2 mb-lg-0'>
            <li className='nav-item'>
              <NavLink className='nav-link' to='/home'>
                Home
              </NavLink>
            </li>
            <li className='nav-item'>
              <NavLink className='nav-link' to='/search'>
                Search Books
              </NavLink>
            </li>
            {authState.isAuthenticated && (
              <li className='nav-item'>
                <NavLink className='nav-link' to='/shelf'>
                  Shelf
                </NavLink>
              </li>
            )}
            {authState.isAuthenticated && authState.userRoles.includes('ADMIN') && (
              <li className='nav-item'>
                <NavLink className='nav-link' to='/admin'>
                  Admin
                </NavLink>
              </li>
            )}
          </ul>
          <ul className='navbar-nav'>
            {!authState.isAuthenticated ? (
              <li className='nav-item m-1'>
                <button type='button' className='btn btn-outline-light' onClick={handleStaticLogin}>
                  Sign in
                </button>
              </li>
            ) : (
              <li>
                <button className='btn btn-outline-light' onClick={handleLogout}>
                  Logout
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};
