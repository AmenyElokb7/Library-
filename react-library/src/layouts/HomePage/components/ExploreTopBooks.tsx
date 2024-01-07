import { Link } from "react-router-dom";

export const ExploreTopBooks = () => {
    return (
        <div className='header bg-dark text-white'>
  <div className='container-fluid py-5'>
    <div className='text-center'>
      <h1 className='display-4 mb-4'>
        <span className="text-bg-black">Discover Your Next Favorite Book</span>
      </h1>
      <p className='lead'>
        <span className="text-bg-black">
          Explore a curated selection of the top books waiting for you.
        </span>
      </p>
      <Link
        to='/search'
        className='btn btn-lg custom-button'
      >
        Explore Top Books
      </Link>
    </div>
  </div>
</div>

      
      
    );
}