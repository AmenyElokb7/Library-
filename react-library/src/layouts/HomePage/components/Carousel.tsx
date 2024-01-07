import React, { useEffect, useState } from 'react';
import BookModel from '../../../models/BookModel';
import { SpinnerLoading } from '../../Utils/SpinnerLoading';
import { Link } from 'react-router-dom';
import { ReturnBook } from './ReturnBook'; // Import the ReturnBook component

export const Carousel = () => {
    const [books, setBooks] = useState<BookModel[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [httpError, setHttpError] = useState<string | null>(null);
  
    useEffect(() => {
        const fetchBooks = async () => {
            const baseUrl: string = 'http://127.0.0.1:8083/api/books';
            const url: string = `${baseUrl}?page=0&size=9`;
          
            try {
              const response = await fetch(url);
          
              if (!response.ok) {
                throw new Error('Something went wrong!');
              }
          
              const responseJson = await response.json();
              const booksData = responseJson._embedded.books;
          
              console.log('API Response Data:', booksData); // Debugging: Print API response
          
              const loadedBooks: BookModel[] = booksData.map((bookData: any) => ({
                id: bookData.id,
                title: bookData.title,
                author: bookData.author,
                description: bookData.description,
                copies: bookData.copies,
                copiesAvailable: bookData.copiesAvailable,
                category: bookData.category,
                img: bookData.img,
              }));
          
              console.log('Loaded Books:', loadedBooks); // Debugging: Print loaded books
          
              setBooks(loadedBooks);
              setIsLoading(false);
            } catch (error: any) {
              setIsLoading(false);
              setHttpError(error.message);
            }
          };
      fetchBooks();
    }, []);

  if (isLoading) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <div className="container m-5">
        <p>{httpError}</p>
      </div>
    );
  }

  return (
    <div className="container mt-5" style={{ height: 550 }}>
      <div className="homepage-carousel-title">
        <h3>Find your next "I stayed up too late reading" book.</h3>
      </div>
      <div
        id="carouselExampleControls"
        className="carousel carousel-dark slide mt-5 d-none d-lg-block"
        data-bs-interval="false"
      >
        {/* Desktop */}
        <div className="carousel-inner">
          {Array.from({ length: Math.ceil(books.length / 3) }, (_, index) => (
            <div
              className={`carousel-item ${index === 0 ? 'active' : ''}`}
              key={`desktop-carousel-item-${index}`}
            >
              <div className="row d-flex justify-content-center align-items-center">
                {books.slice(index * 3, index * 3 + 3).map((book) => (
                  <div className="col-xs-6 col-sm-6 col-md-4 col-lg-3 mb-3" key={book.id}>
                    <div className="text-center">
                      {book.img ? (
                        <img src={book.img} width="151" height="233" alt="book" />
                      ) : (
                        <img
                          src={require('./../../../Images/BooksImages/book-luv2code-1000.png')}
                          width="151"
                          height="233"
                          alt="book"
                        />
                      )}
                      <h6 className="mt-2">{book.title}</h6>
                      <p>{book.author}</p>
                      <Link className="btn main-color text-white" to={`checkout/${book.id}`}>
                        Reserve
                      </Link>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <button
          className="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleControls"
          data-bs-slide="prev"
        >
          <span className="carousel-control-prev-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button
          className="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleControls"
          data-bs-slide="next"
        >
          <span className="carousel-control-next-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>
  
      {/* Mobile */}
      <div className="d-lg-none mt-3">
        <div className="row d-flex justify-content-center align-items-center">
          {books.length > 7 && (
            <div className="col-xs-6 col-sm-6 col-md-4 col-lg-3 mb-3" key={books[7].id}>
              <div className="text-center">
                {books[7].img ? (
                  <img src={books[7].img} width="151" height="233" alt="book" />
                ) : (
                  <img
                    src={require('./../../../Images/BooksImages/book-luv2code-1000.png')}
                    width="151"
                    height="233"
                    alt="book"
                  />
                )}
                <h6 className="mt-2">{books[7].title}</h6>
                <p>{books[7].author}</p>
                <Link className="btn main-color text-white" to={`checkout/${books[7].id}`}>
                  Reserve
                </Link>
              </div>
            </div>
          )}
        </div>
      </div>
      <div className="homepage-carousel-title mt-3">
        <Link className="btn btn-outline-secondary btn-lg" to="/search">
          View More
        </Link>
      </div>
    </div>
  );
                }  