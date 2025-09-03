import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";

const ViewAllJourneyBooking = () => {
  const navigate = useNavigate();

  const admin_token = sessionStorage.getItem("admin-jwtToken");

  const [bookedJourneys, setBookedJourneys] = useState([]);

  const retrieveAllBookedJourneys = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/journey/book/fetch/all",
      {
        headers: {
          Authorization: "Bearer " + admin_token, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  const convertToEpochTime = (dateString) => {
    const selectedDate = new Date(dateString);
    const epochTime = selectedDate.getTime();
    return epochTime;
  };

  useEffect(() => {
    const getAllBookedJourneys = async () => {
      const bookings = await retrieveAllBookedJourneys();
      if (bookings) {
        setBookedJourneys(bookings.bookings);
      }
    };

    getAllBookedJourneys();
  }, []);

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const downloadTicket = (bookingId) => {
    fetch(
      `http://localhost:8080/api/journey/book/download/ticket?bookingId=${bookingId}`,
      {
        method: "GET",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: "Bearer " + admin_token,
        },
      }
    )
      .then((response) => response.blob())
      .then((blob) => {
        // Create a temporary URL for the blob
        const url = window.URL.createObjectURL(blob);

        // Create a temporary <a> element to trigger the download
        const link = document.createElement("a");
        link.href = url;
        link.download = "ticket.pdf"; // Specify the desired filename here

        // Append the link to the document and trigger the download
        document.body.appendChild(link);
        link.click();

        // Clean up the temporary URL and link
        URL.revokeObjectURL(url);
        document.body.removeChild(link);
      })
      .catch((error) => {
        console.error("Download error:", error);
      });
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 border-color "
        style={{
          height: "45rem",
        }}
      >
        <div className="card-header custom-bg text-center text-color">
          <h2>Booked Journeys</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive mt-3">
            <table className="table table-hover text-color text-center">
              <thead className="table-bordered border-color text-color custom-bg">
                <tr>
                  <th scope="col">Booking Id</th>
                  <th scope="col">Passenger</th>
                  <th scope="col">Passenger Contact</th>
                  <th scope="col">Journey Number</th>
                  <th scope="col">Bus</th>
                  <th scope="col">Bus Registration No.</th>
                  <th scope="col">Departure Time</th>
                  <th scope="col">Arrival Time</th>
                  <th scope="col">Source BusStop</th>
                  <th scope="col">Destination BusStop</th>
                  <th scope="col">Journey Class</th>
                  <th scope="col">Seat Fare (Rs.)</th>
                  <th scope="col">Total Passenger</th>
                  <th scope="col">Booking Time</th>
                  <th scope="col">Status</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {bookedJourneys.map((book) => {
                  return (
                    <tr>
                      <td>
                        <b>{book.bookingId}</b>
                      </td>
                      <td>
                        <b>{book.passenger.name}</b>
                      </td>
                      <td>
                        <b>{book.passenger.contact}</b>
                      </td>
                      <td>
                        <b>{book.journey.journeyNumber}</b>
                      </td>
                      <td>
                        <b>{book.journey.bus.name}</b>
                      </td>
                      <td>
                        <b>{book.journey.bus.registrationNumber}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(book.journey.departureTime)}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(book.journey.arrivalTime)}</b>
                      </td>
                      <td>
                        <b>{book.journey.departureBusStop.name}</b>
                      </td>
                      <td>
                        <b>{book.journey.arrivalBusStop.name}</b>
                      </td>
                      <td>
                        <b>{book.journeyClass}</b>
                      </td>
                      <td>
                        {(() => {
                          if (book.journeyClass === "Back") {
                            return <b>{book.journey.backSeatFare}</b>;
                          } else if (book.journeyClass === "Middle") {
                            return <b>{book.journey.middleSeatFare}</b>;
                          } else if (book.journeyClass === "Front") {
                            return <b>{book.journey.frontSeatFare}</b>;
                          }
                        })()}
                      </td>
                      <td>
                        {(() => {
                          if (book.busSeatNo !== null) {
                            return <b> {book.busSeatNo.seatNo}</b>;
                          }
                        })()}
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(book.bookingTime)}</b>
                      </td>
                      <td>
                        <b>{book.status}</b>
                      </td>
                      <td>
                        {(() => {
                          if (book.status === "Confirmed") {
                            return (
                              <button
                                onClick={() => downloadTicket(book.bookingId)}
                                className="btn btn-sm bg-color custom-bg-text ms-2"
                              >
                                Download Ticket
                              </button>
                            );
                          }
                        })()}

                        <ToastContainer />
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewAllJourneyBooking;
