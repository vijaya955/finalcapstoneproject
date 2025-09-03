const ContactUs = () => {
  return (
    <div className="text-color ms-5 me-5 mr-5 mt-3">
      <h2 className="mb-3">Contact Us</h2>
      <p>
        We’re here to help you with your bookings, queries, or any travel-related
        assistance. Reach out to us using the details below:
      </p>

      {/* Customer Support */}
      <h4 className="mt-4">Customer Support</h4>
      <p>
        <b>Email:</b>{" "}
        <a href="mailto:support@busreservation.com" className="text-decoration-none">
          support@busreservation.com
        </a>
        <br />
        <b>Phone:</b>{" "}
        <a href="tel:+919876543210" className="text-decoration-none">
          +91 98765 43210
        </a>
        <br />
        <b>Working Hours:</b> 9:00 AM – 9:00 PM (Mon–Sat)
      </p>

      {/* Office Address */}
      <h4 className="mt-4">Head Office</h4>
      <p>
        Bus Reservation Pvt. Ltd. <br />
        123 Main Road, Hyderabad, India <br />
        PIN: 500001
      </p>

      {/* Social Media */}
      <h4 className="mt-4">Follow Us</h4>
      <ul className="list-unstyled d-flex gap-4">
        <li>
          <a href="https://facebook.com" target="_blank" rel="noreferrer" className="text-decoration-none">
            <i className="bi bi-facebook me-1"></i> Facebook
          </a>
        </li>
        <li>
          <a href="https://twitter.com" target="_blank" rel="noreferrer" className="text-decoration-none">
            <i className="bi bi-twitter me-1"></i> Twitter
          </a>
        </li>
        <li>
          <a href="https://instagram.com" target="_blank" rel="noreferrer" className="text-decoration-none">
            <i className="bi bi-instagram me-1"></i> Instagram
          </a>
        </li>
      </ul>

      {/* Feedback */}
      <h4 className="mt-4">Feedback</h4>
      <p>
        We value your feedback! Share your travel experience or suggestions by
        emailing us at{" "}
        <a href="mailto:feedback@busreservation.com" className="text-decoration-none">
          feedback@busreservation.com
        </a>
        . Your input helps us improve our services.
      </p>

      {/* Contact Form */}
      <h4 className="mt-4">Send Us a Message</h4>
      <form className="mb-5">
        <div className="mb-3">
          <label className="form-label">Your Name</label>
          <input type="text" className="form-control" placeholder="Enter your name" required />
        </div>
        <div className="mb-3">
          <label className="form-label">Your Email</label>
          <input type="email" className="form-control" placeholder="Enter your email" required />
        </div>
        <div className="mb-3">
          <label className="form-label">Message</label>
          <textarea className="form-control" rows="4" placeholder="Write your message..." required></textarea>
        </div>
        <button type="submit" className="btn btn-primary">Send</button>
      </form>
    </div>
  );
};

export default ContactUs;
