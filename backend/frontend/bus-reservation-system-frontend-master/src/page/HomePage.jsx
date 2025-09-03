import Carousel from "./Carousel";
import Footer from "./Footer";
import { Link } from "react-router-dom";
import travel_1 from "../images/travel_1.png";
import travel_2 from "../images/travel_2.png";

const HomePage = () => {
  return (
    <div className="container-fluid mb-2">
      <Carousel />

      <div className="container mt-5">
        <div className="row">
          <div className="col-md-8">
            <h1 className="text-color">Welcome to MyBus</h1>
            <p>
              Your hassle-free travel companion is here! With our Smart Bus
              Reservation System, booking tickets has never been easier.
              Discover a wide range of routes, schedules, and seating options
              tailored to your needs – all just a click away.
            </p>
            <p>
              Whether you’re commuting to work, planning a weekend getaway, or
              heading home to see loved ones, our platform makes sure your
              journey is smooth, reliable, and comfortable. Experience travel
              that’s affordable, simple, and secure.
            </p>
            <Link to="/user/login" className="btn text-color custom-bg">
              <b>Start Booking</b>
            </Link>
          </div>
          <div className="col-md-4">
            <img
              src={travel_2}
              alt="Travel"
              width="400"
              height="auto"
              className="home-image"
            />
          </div>
        </div>

        <div className="row mt-5">
          <div className="col-md-4">
            <img
              src={travel_1}
              alt="Bus Travel"
              width="400"
              height="auto"
              className="home-image"
            />
          </div>
          <div className="col-md-8">
            <h1 className="text-color ms-5">
              Why Choose Our Bus Reservation System?
            </h1>
            <p className="ms-5">
              🚍 <b>Real-Time Availability:</b> Instantly check available buses,
              routes, and seats with our live updates.  
            </p>
            <p className="ms-5">
              ⚡ <b>Instant Booking & Confirmation:</b> Reserve your seat in just
              a few clicks and get immediate booking confirmation via email or
              SMS.  
            </p>
            <p className="ms-5">
              🛡️ <b>Safe & Comfortable Journeys:</b> Travel with trusted bus
              operators who prioritize your comfort and safety every mile of the
              way.  
            </p>
            <p className="ms-5">
              💰 <b>Affordable Travel:</b> Get the best value for your money
              with transparent pricing and no hidden charges.  
            </p>
            <Link to="/user/login" className="btn text-color custom-bg ms-5">
              <b>Book Now</b>
            </Link>
          </div>
        </div>
      </div>
      <hr />
      <Footer />
    </div>
  );
};

export default HomePage;
