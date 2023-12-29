// App.jsx
import React from 'react';
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import { AuthProvider } from './AuthContext';
import { BookCheckoutPage } from './layouts/BookCheckoutPage/BookCheckoutPage';
import { HomePage } from './layouts/HomePage/HomePage';
import { Footer } from './layouts/NavbarAndFooter/Footer';
import { Navbar } from './layouts/NavbarAndFooter/Navbar';
import { SearchBooksPage } from './layouts/SearchBooksPage/SearchBooksPage';
import { ReviewListPage } from './layouts/BookCheckoutPage/ReviewListPage/ReviewListPage';
import { ShelfPage } from './layouts/ShelfPage/ShelfPage';
import { MessagesPage } from './layouts/MessagesPage/MessagesPage';
import { ManageLibraryPage } from './layouts/ManageLibraryPage/ManageLibraryPage';
import Login from './Auth/Login';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <div className='d-flex flex-column min-vh-100'>
          <Navbar />
          <div className='flex-grow-1'>
            <Switch>
              <Route path='/' exact>
                <Redirect to='/home' />
              </Route>
              <Route path='/home' component={HomePage} />
              <Route path='/search' component={SearchBooksPage} />
              <Route path='/reviewlist/:bookId' component={ReviewListPage} />
              <Route path='/checkout/:bookId' component={BookCheckoutPage} />
              <Route path='/login' component={Login}>
              </Route>
              <Route path='/shelf' component={ShelfPage} />
              <Route path='/messages' component={MessagesPage} />
              <Route path='/admin' component={ManageLibraryPage} />
            </Switch>
          </div>
          <Footer />
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;
