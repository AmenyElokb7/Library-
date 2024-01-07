import { Link, NavLink } from "react-router-dom";
import { useState, useEffect } from 'react';
import { SpinnerLoading } from "../Utils/SpinnerLoading";

export const Navbar = (props) => {
  const isLoading = false;

  const userItem = localStorage.getItem("user");
  let loggedinUser = null;
  
  if (userItem) {
    loggedinUser = JSON.parse(userItem);
  }
  
  let userRole = loggedinUser?.role;
  console.log(userRole);
  const isAdmin = userRole === '[ROLE_ADMIN]';
  const isAuthenticated = userRole && userRole.length > 0; // Check if userRole is not null or empty string
  
  console.log(isAuthenticated);
  console.log(isAdmin);
  const handleLogout = async () => {
    localStorage.clear();
    window.location.reload();
  };

  if (isLoading) {
    return <SpinnerLoading />;
  }

  return (
    <nav className='navbar navbar-expand-lg navbar-dark' style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)', backdropFilter: 'blur(px)' }}>
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
            <NavLink className='nav-link' to='/home'>Home</NavLink>

            </li>
            <li className='nav-item'>
            <NavLink className='nav-link' to='/search'>Search Books</NavLink>

            </li>
            {isAuthenticated ?
              <li className='nav-item'>
                <NavLink className='nav-link' to='/shelf'>Shelf</NavLink>
              </li>:''
            }
            {isAuthenticated && !isAdmin ?
              <li className='nav-item'>
                <NavLink className='nav-link' to='/messages'>Messages</NavLink>
              </li>:''
            }
            {isAdmin ?
              <li className='nav-item'>
                <NavLink className='nav-link' to='/admin'>Admin</NavLink>
              </li>
              :''
            }
          </ul>
          <ul className='navbar-nav'>
          {!isAuthenticated ?
          <li className='nav-item m-1'>
            <Link type='button' className='btn btn-outline-light' to='/login'>Sign in</Link>
          </li>
          :
          <li>
            <button className='btn btn-outline-light' onClick={handleLogout}>Logout</button>
          </li>
          }
          </ul>
        </div>
      </div>
    </nav>
  );
}