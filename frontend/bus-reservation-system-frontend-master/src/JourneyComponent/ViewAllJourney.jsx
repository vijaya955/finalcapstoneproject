import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";

const ViewAllJourney = () => {
  const navigate = useNavigate();

  var passenger = JSON.parse(sessionStorage.getItem("active-passenger"));
  var admin = JSON.parse(sessionStorage.getItem("active-admin"));

  const [searchRequest, setSearchRequest] = useState({
    startTime: "",
    endTime: "",
    fromBusStopId: "",
    toBusStopId: "",
  });

  const [tempSearchRequest, setTempSearchRequest] = useState({
    startTime: "",
    endTime: "",
    fromBusStopId: "",
    toBusStopId: "",
  });

  const handleTempSearchInput = (e) => {
    setTempSearchRequest({
      ...tempSearchRequest,
      [e.target.name]: e.target.value,
    });
  };

  const [busStops, setBusStops] = useState([]);

  const retrieveAllBusStops = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/busStop/fetch/all"
    );
    return response.data;
  };

  useEffect(() => {
    const getAllBusStops = async () => {
      const allBusStops = await retrieveAllBusStops();
      if (allBusStops) {
        setBusStops(allBusStops.busStops);
      }
    };

    getAllBusStops();
  }, []);

  const [scheduledJourneys, setScheduledJourneys] = useState([]);

  const retrieveAllScheduledJourneys = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/journey/fetch/all"
    );
    console.log(response.data);
    return response.data;
  };

  const searchScheduledJourneysByUserInput = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/journey/search?startTime=" +
        convertToEpochTime(searchRequest.startTime) +
        "&endTime=" +
        convertToEpochTime(searchRequest.endTime) +
        "&fromBusStopId=" +
        searchRequest.fromBusStopId +
        "&endBusStopId=" +
        searchRequest.toBusStopId
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
    const getAllScheduledJourneys = async () => {
      if (
        searchRequest.startTime &&
        searchRequest.endTime &&
        searchRequest.fromBusStopId &&
        searchRequest.toBusStopId
      ) {
        const journeys = await searchScheduledJourneysByUserInput();
        if (journeys) {
          setScheduledJourneys(journeys.journeys);
        }
      } else {
        const journeys = await retrieveAllScheduledJourneys();
        if (journeys) {
          setScheduledJourneys(journeys.journeys);
        }
      }
    };

    getAllScheduledJourneys();
  }, [searchRequest]);

  const bookSeat = (journey) => {
    navigate("/passenger/journey/book", { state: journey });
  };

  const updateStatus = (journey) => {
    navigate("/admin/journey/status/update", { state: journey });
  };

  const navigateToViewJourneyBookingStatus = (journey) => {
    navigate("/journey/booking/status/", { state: journey });
  };

  const searchScheduledTrains = (e) => {
    setSearchRequest(tempSearchRequest);
    e.preventDefault();
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 border-color "
        style={{
          height: "45rem",
        }}
      >
        <div className="card-header text-center text-color custom-bg">
          <h2>Scheduled Journeys</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="row">
            <div className="col">
              <form class="row g-3 align-items-center">
                <div class="col-auto">
                  <label>
                    <b>Select Start Date</b>
                  </label>
                  <input
                    type="date"
                    class="form-control"
                    name="startTime"
                    placeholder="Start Time..."
                    onChange={handleTempSearchInput}
                    value={tempSearchRequest.startTime}
                    required
                  />
                </div>
                <div class="col-auto">
                  <label>
                    <b>Select End Date</b>
                  </label>
                  <input
                    type="date"
                    class="form-control"
                    name="endTime"
                    placeholder="Start Date..."
                    onChange={handleTempSearchInput}
                    value={tempSearchRequest.endTime}
                    required
                  />
                </div>

                <div className="col-auto">
                  <label>
                    <b>From BusStop</b>
                  </label>
                  <select
                    onChange={handleTempSearchInput}
                    className="form-control"
                    name="fromBusStopId"
                    required
                  >
                    <option value="">Select Source BusStop</option>

                    {busStops.map((busStop) => {
                      return (
                        <option value={busStop.id}> {busStop.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div className="col-auto">
                  <label>
                    <b>To BusStop</b>
                  </label>
                  <select
                    onChange={handleTempSearchInput}
                    className="form-control"
                    name="toBusStopId"
                    required
                  >
                    <option value="">Select Destination BusStop</option>

                    {busStops.map((busStop) => {
                      return (
                        <option value={busStop.id}> {busStop.name} </option>
                      );
                    })}
                  </select>
                </div>

                <div class="col-auto">
                  <button
                    type="submit"
                    class="btn text-color custom-bg btn-lg"
                    onClick={searchScheduledTrains}
                  >
                    <b>Search</b>
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div className="table-responsive mt-3">
            <table className="table table-hover text-color text-center">
              <thead className="table-bordered border-color text-color custom-bg">
                <tr>
                  <th scope="col">Journey Number</th>
                  <th scope="col">Bus</th>
                  <th scope="col">Bus Registration</th>
                  <th scope="col">Departure Time</th>
                  <th scope="col">Arrival Time</th>
                  <th scope="col">Source BusStop</th>
                  <th scope="col">Destination BusStop</th>
                  <th scope="col">Back Seat Fare (Rs.)</th>
                  <th scope="col">Middle Seat Fare (Rs.)</th>
                  <th scope="col">Front Seat Fare (Rs.)</th>
                  <th scope="col">Status</th>
                  <th scope="col">Action</th>
                </tr>
              </thead>
              <tbody>
                {scheduledJourneys.map((journey) => {
                  return (
                    <tr>
                      <td>
                        <b>{journey.journeyNumber}</b>
                      </td>
                      <td>
                        <b>{journey.bus.name}</b>
                      </td>
                      <td>
                        <b>{journey.bus.registrationNumber}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(journey.departureTime)}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(journey.arrivalTime)}</b>
                      </td>
                      <td>
                        <b>{journey.departureBusStop.name}</b>
                      </td>
                      <td>
                        <b>{journey.arrivalBusStop.name}</b>
                      </td>
                      <td>
                        <b>{journey.backSeatFare}</b>
                      </td>
                      <td>
                        <b>{journey.middleSeatFare}</b>
                      </td>
                      <td>
                        <b>{journey.frontSeatFare}</b>
                      </td>
                      <td>
                        <b>{journey.status}</b>
                      </td>

                      <td>
                        {(() => {
                          if (admin !== null) {
                            return (
                              <div>
                                <button
                                  onClick={() => updateStatus(journey)}
                                  className="btn btn-sm text-color custom-bg ms-2"
                                >
                                  <b> Update Status</b>
                                </button>

                                <button
                                  onClick={() =>
                                    navigateToViewJourneyBookingStatus(journey)
                                  }
                                  className="btn btn-sm text-color custom-bg"
                                >
                                  <b> Book Seat</b>
                                </button>
                              </div>
                            );
                          } else {
                            return (
                              <button
                                onClick={() =>
                                  navigateToViewJourneyBookingStatus(journey)
                                }
                                className="btn btn-sm text-color custom-bg"
                              >
                                <b>Book Seat</b>
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

export default ViewAllJourney;
