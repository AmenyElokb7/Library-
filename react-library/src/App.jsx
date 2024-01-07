import { Redirect, Route, Switch, useHistory } from 'react-router-dom';
import './App.css';
import { BookCheckoutPage } from './layouts/BookCheckoutPage/BookCheckoutPage';
import { HomePage } from './layouts/HomePage/HomePage';
import { Footer } from './layouts/NavbarAndFooter/Footer';
import { Navbar } from './layouts/NavbarAndFooter/Navbar';
import { SearchBooksPage } from './layouts/SearchBooksPage/SearchBooksPage';
import LoginWidget from './Auth/LoginWidget';
import { ReviewListPage } from './layouts/BookCheckoutPage/ReviewListPage/ReviewListPage';
import { ShelfPage } from './layouts/ShelfPage/ShelfPage';
import { MessagesPage } from './layouts/MessagesPage/MessagesPage';
import { ManageLibraryPage } from './layouts/ManageLibraryPage/ManageLibraryPage';
import { useState, useEffect } from 'react';


export const App = () => {
  
  const [userRole, setUserRole] = useState(JSON.parse(localStorage.getItem("user"))?.role);

  const isAdmin = userRole === '[ROLE_ADMIN]';
  const isAuthenticated = userRole && userRole.length > 0; // Check if userRole is not null or empty string

  useEffect(() => {
    setUserRole(JSON.parse(localStorage.getItem("user"))?.role);
  }, []);

  const handleLogin = (role) => {
    const updatedUser = JSON.stringify({ ...JSON.parse(localStorage.getItem("user")), role });
    localStorage.setItem("user", updatedUser);
    setUserRole(role);
    console.log(updatedUser);
    
  };

  return (
    <div className='d-flex flex-column min-vh-100'>
      {/* Security component removed for simplicity */}
      <Navbar authUser={userRole} />
      <div className='flex-grow-1'>
        <Switch>
          <Route path='/' exact>
            <Redirect to='/home' />
          </Route>
          <Route path='/home'>
            <HomePage authUser={userRole} />
          </Route>
          <Route path='/search'>
            <SearchBooksPage />
          </Route>
          <Route path='/reviewlist/:bookId'>
            <ReviewListPage/>
          </Route>
          <Route path='/checkout/:bookId'>
            <BookCheckoutPage/>
          </Route>
          {/* Secure /admin route */}
          <Route path='/admin'>
            {isAdmin ? <ManageLibraryPage authUser={userRole}/> : <Redirect to='/login' />}
          </Route>
          {/* Secure /shelf route */}
          <Route path='/shelf'>
          {isAuthenticated ? <ShelfPage authUser={userRole}/> : <Redirect to='/login'/>}  
          </Route>
          {/* Secure /messages route */}
          <Route path='/messages'>
            {isAuthenticated ? <MessagesPage authUser={userRole}/> : <Redirect to='/login'/>} 
          </Route>
          {/* Login route */}
          <Route path='/login'>
            <LoginWidget onLogin={handleLogin} />
          </Route>
          {/* Other routes as before */}
        </Switch>
      </div>
      <Footer />
    </div>
  );
}