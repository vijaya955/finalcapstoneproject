import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <div>
      <div className="container my-5">
        <footer className="text-center text-lg-start text-color">
          <div className="container-fluid p-4 pb-0">
            <div className="row">
              {/* Main Intro */}
              <div className="col-lg-4 col-md-6 mb-4 mb-md-0">
                <h5 className="text-uppercase text-color">
                  Bus Reservation System
                </h5>
                <p className="text-muted small">
                  Book your tickets online with ease and travel hassle-free.
                </p>
              </div>

              {/* About Us */}
              <div className="col-lg-2 col-md-6 mb-4 mb-md-0">
                <h5 className="text-uppercase text-color-4">
                  <Link
                    to="/about"
                    className="text-decoration-none text-color-4"
                  >
                    About Us
                  </Link>
                </h5>
              </div>

              {/* Contact Us */}
              <div className="col-lg-2 col-md-6 mb-4 mb-md-0">
                <h5 className="text-uppercase text-color-4">
                  <Link
                    to="/contact"
                    className="text-decoration-none text-color-4"
                  >
                    Contact Us
                  </Link>
                </h5>
              </div>

              {/* Careers */}
              <div className="col-lg-2 col-md-6 mb-4 mb-md-0">
                <h5 className="text-uppercase text-color-4">
                  <Link
                    to="/careers"
                    className="text-decoration-none text-color-4"
                  >
                    Careers
                  </Link>
                </h5>
              </div>

              {/* Useful Links */}
              <div className="col-lg-2 col-md-6 mb-4 mb-md-0">
                <h5 className="text-uppercase text-color-4">Quick Links</h5>
                <ul className="list-unstyled">
                  <li>
                    <Link to="/" className="text-decoration-none text-color-4">
                      Home
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/buses"
                      className="text-decoration-none text-color-4"
                    >
                      Book Tickets
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/user/register"
                      className="text-decoration-none text-color-4"
                    >
                      Sign Up
                    </Link>
                  </li>
                </ul>
              </div>
            </div>

            <hr className="mb-4" />

            {/* Social Media Section */}
            <div className="text-center pb-3">
              <a href="https://facebook.com" className="me-3 text-color-4">
                <i className="bi bi-facebook"></i>
              </a>
              <a href="https://twitter.com" className="me-3 text-color-4">
                <i className="bi bi-twitter"></i>
              </a>
              <a href="https://instagram.com" className="me-3 text-color-4">
                <i className="bi bi-instagram"></i>
              </a>
              <a href="https://linkedin.com" className="text-color-4">
                <i className="bi bi-linkedin"></i>
              </a>
            </div>

            {/* Copyright */}
            <div className="text-center p-3 text-muted" style={{fontSize: "14px"}}>
              © {new Date().getFullYear()} Bus Reservation System | All Rights Reserved
            </div>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default Footer;
