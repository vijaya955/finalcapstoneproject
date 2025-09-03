import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useState, useEffect } from "react";

const ViewScheduledJourneyBookings = () => {
  const navigate = useNavigate();

  const location = useLocation();
  const journey = location.state;

  const [scheduledJourneyBookings, setScheduledJourneyBookings] = useState([]);

  const [journeySeatDetail, setJourneySeatDetail] = useState({});

  const retrieveScheduleJourneyBookings = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/journey/book/fetch?journeyId=" + journey.id
    );
    console.log(response.data);
    return response.data;
  };

  const retrieveJourneySeatDetails = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/journey/book/fetch/seatDetails?journeyId=" +
        journey.id
    );
    console.log(response.data);
    return response.data;
  };

  useEffect(() => {
    const getJourneyBookings = async () => {
      const journeyTickets = await retrieveScheduleJourneyBookings();
      if (journeyTickets) {
        setScheduledJourneyBookings(journeyTickets.bookings);
      }
    };

    const getJourneySeatDetails = async () => {
      const journeySeatDetails = await retrieveJourneySeatDetails();
      if (journeySeatDetails) {
        setJourneySeatDetail(journeySeatDetails);
      }
    };

    getJourneyBookings();
    getJourneySeatDetails();
  }, []);

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  const bookJourneyTicket = () => {
    navigate("/passenger/journey/book", { state: journey });
  };

  return (
    <div className="mt-3">
      <div className="d-flex justify-content-center align-items-center">
        <div
          className="card form-card ms-2 me-2 mb-5 custom-bg border-color "
          style={{
            height: "45rem",
            width: "40rem",
          }}
        >
          <div className="card-header custom-bg text-center text-color">
            <h2>Check Journey Ticket Availablity</h2>
          </div>
          <div className="card-body" style={{ overflowY: "auto" }}>
            <div className="row">
              <div className="col-md-6">
                <div className="mt-3">
                  <b>Journey Number:</b>
                  <h5 className="text-color"> {journey.journeyNumber}</h5>
                </div>
                <div className="mt-3">
                  <b>Bus:</b>
                  <h5 className="text-color"> {journey.bus.name}</h5>
                </div>
                <div className="mt-3">
                  <b>Departure BusStop:</b>
                  <h5 className="text-color">
                    {journey.departureBusStop.name}
                  </h5>
                </div>
                <div className="mt-3">
                  <b>Arrival BusStop:</b>
                  <h5 className="text-color"> {journey.arrivalBusStop.name}</h5>
                </div>
                <div className="mt-3">
                  <b>Departure Timing:</b>
                  <h5 className="text-color">
                    {formatDateFromEpoch(journey.departureTime)}
                  </h5>
                </div>
                <div className="mt-3">
                  <b>Arrival Timing:</b>
                  <h5 className="text-color">
                    {formatDateFromEpoch(journey.arrivalTime)}
                  </h5>
                </div>

                <div className="mt-3">
                  <b>Back Seat Price (in Rs):</b>
                  <h5 className="text-color"> {journey.backSeatFare}</h5>
                </div>
                <div className="mt-3">
                  <b>Middle Seat Price (in Rs):</b>
                  <h5 className="text-color"> {journey.middleSeatFare}</h5>
                </div>
                <div className="mt-3">
                  <b>Front Seat Price (in Rs):</b>
                  <h5 className="text-color"> {journey.frontSeatFare}</h5>
                </div>

                <hr className="my-4" />
                <div className="mt-3">
                  <b>Total Seat:</b>
                  <h5 className="text-color">{journey.totalSeat}</h5>
                </div>
                <div className="mt-3">
                  <b>Total Back Seat:</b>
                  <h5 className="text-color">{journeySeatDetail.backSeats}</h5>
                </div>
                <div className="mt-3">
                  <b>Total Back Seat Available:</b>
                  <h5 className="text-color">
                    {journeySeatDetail.backSeatsAvailable}
                  </h5>
                </div>
                <div className="mt-3">
                  <b>Total Middle Seat:</b>
                  <h5 className="text-color">
                    {journeySeatDetail.middleSeats}
                  </h5>
                </div>
                <div className="mt-3">
                  <b>Total Middle Seat Available:</b>
                  <h5 className="text-color">
                    {journeySeatDetail.middleSeatsAvailable}
                  </h5>
                </div>
                <div className="mt-3">
                  <b>Total Front Seat:</b>
                  <h5 className="text-color">{journeySeatDetail.frontSeats}</h5>
                </div>
                <div className="mt-3">
                  <b>Total Front Seat Available:</b>
                  <h5 className="text-color">
                    {journeySeatDetail.frontSeatsAvailable}
                  </h5>
                </div>
              </div>
              <div className="col-md-5">
                <div className="table-responsive" style={{ float: "right" }}>
                  <div className="text-center">
                    <h4 className="text-color">Journey Seats</h4>
                  </div>
                  <table className="table table-hover text-color text-center">
                    <thead className="table-bordered border-color bg-color custom-bg-text">
                      <tr>
                        <th scope="col">Journey Seat</th>
                        <th scope="col">Booking Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      {scheduledJourneyBookings.map((booking) => {
                        return (
                          <tr>
                            <td>
                              <b>{booking.busSeatNo.seatNo}</b>
                            </td>
                            <td>
                              <b>{booking.status}</b>
                            </td>
                          </tr>
                        );
                      })}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>

            <div className="mt-3 d-flex justify-content-center align-items-center">
              <button
                onClick={() => bookJourneyTicket()}
                className="btn btn-lg bg-dark text-white"
              >
                Book Ticket
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewScheduledJourneyBookings;
