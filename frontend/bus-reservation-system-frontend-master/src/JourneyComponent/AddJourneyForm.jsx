import { useState, useEffect } from "react";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const AddJourneyForm = () => {
  const navigate = useNavigate();

  const admin_token = sessionStorage.getItem("admin-jwtToken");

  const [journey, setJourney] = useState({
    departureTime: "",
    arrivalTime: "",
    departureBusStopId: "",
    arrivalBusStopId: "",
    busId: "",
    status: "",
    backSeatFare: "",
    middleSeatFare: "",
    frontSeatFare: "",
  });

  const handleUserInput = (e) => {
    setJourney({ ...journey, [e.target.name]: e.target.value });
  };

  const [departureTime, setDepartureTime] = useState("");
  const [arrivalTime, setArrivalTime] = useState("");

  const [allBuss, setAllBuss] = useState([]);
  const [allBusStops, setAllBusStops] = useState([]);
  const [allStatus, setAllStatus] = useState([]);

  const retrieveAllBuss = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/bus/fetch/all",
      {
        headers: {
          Authorization: "Bearer " + admin_token, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  const retrieveAllBusStop = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/busStop/fetch/all"
    );
    console.log(response.data);
    return response.data;
  };

  const retrieveAllJourneyStatus = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/journey/status/all",
      {
        headers: {
          Authorization: "Bearer " + admin_token, // Replace with your actual JWT token
        },
      }
    );
    console.log(response.data);
    return response.data;
  };

  useEffect(() => {
    const getAllBuss = async () => {
      const allBuss = await retrieveAllBuss();
      if (allBuss) {
        setAllBuss(allBuss.buses);
      }
    };

    const getAllBusStops = async () => {
      const allBusStops = await retrieveAllBusStop();
      if (allBusStops) {
        setAllBusStops(allBusStops.busStops);
      }
    };

    const getAllJourneyStatus = async () => {
      const allStatuses = await retrieveAllJourneyStatus();
      if (allStatuses) {
        setAllStatus(allStatuses);
      }
    };

    getAllBuss();
    getAllBusStops();
    getAllJourneyStatus();
  }, []);

  const saveJourney = (e) => {
    const departureEpochTime = new Date(departureTime).getTime();
    journey.departureTime = departureEpochTime;

    const arrivalEpochTime = new Date(arrivalTime).getTime();
    journey.arrivalTime = arrivalEpochTime;

    fetch("http://localhost:8080/api/journey/add", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: "Bearer " + admin_token,
      },
      body: JSON.stringify(journey),
    })
      .then((result) => {
        console.log("result", result);
        result.json().then((res) => {
          console.log(res);

          if (res.success) {
            console.log("Got the success response");

            toast.success(res.responseMessage, {
              position: "top-center",
              autoClose: 1000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });

            setTimeout(() => {
              navigate("/admin/journey/all");
            }, 1000); // Redirect after 3 seconds
          } else {
            console.log("Didn't got success response");
            toast.error("It seems server is down", {
              position: "top-center",
              autoClose: 1000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            // setTimeout(() => {
            //   window.location.reload(true);
            // }, 1000); // Redirect after 3 seconds
          }
        });
      })
      .catch((error) => {
        console.error(error);
        toast.error("It seems server is down", {
          position: "top-center",
          autoClose: 1000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        // setTimeout(() => {
        //   window.location.reload(true);
        // }, 1000); // Redirect after 3 seconds
      });
    e.preventDefault();
  };

  return (
    <div>
      <div className="mt-2 d-flex aligns-items-center justify-content-center ms-2 me-2 mb-2">
        <div
          className="card form-card border-color text-color"
          style={{ width: "50rem" }}
        >
          <div className="card-header text-color custom-bg text-center">
            <h5 className="card-title">Add Journey</h5>
          </div>
          <div className="card-body">
            <form className="row g-3" onSubmit={saveJourney}>
              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="trainId" className="form-label">
                  <b>Bus</b>
                </label>
                <select
                  onChange={handleUserInput}
                  className="form-control"
                  name="busId"
                >
                  <option value="">Select Bus</option>

                  {allBuss.map((bus) => {
                    return <option value={bus.id}> {bus.name} </option>;
                  })}
                </select>
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="trainId" className="form-label">
                  <b>Departure BusStop</b>
                </label>
                <select
                  onChange={handleUserInput}
                  className="form-control"
                  name="departureBusStopId"
                >
                  <option value="">Select Departure BusStop</option>

                  {allBusStops.map((busStop) => {
                    return <option value={busStop.id}> {busStop.name} </option>;
                  })}
                </select>
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="trainId" className="form-label">
                  <b>Arrival BusStop</b>
                </label>
                <select
                  onChange={handleUserInput}
                  className="form-control"
                  name="arrivalBusStopId"
                >
                  <option value="">Select Arrival BusStop</option>

                  {allBusStops.map((busStop) => {
                    return <option value={busStop.id}> {busStop.name} </option>;
                  })}
                </select>
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="trainId" className="form-label">
                  <b>Journey Status</b>
                </label>
                <select
                  onChange={handleUserInput}
                  className="form-control"
                  name="status"
                >
                  <option value="">Select Status</option>

                  {allStatus.map((status) => {
                    return <option value={status}> {status} </option>;
                  })}
                </select>
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Select Departure Time</b>
                </label>
                <input
                  type="datetime-local"
                  className="form-control"
                  value={departureTime}
                  onChange={(e) => setDepartureTime(e.target.value)}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Select Arrival Time</b>
                </label>
                <input
                  type="datetime-local"
                  className="form-control"
                  value={arrivalTime}
                  onChange={(e) => setArrivalTime(e.target.value)}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <b>
                  <label className="form-label">Back Seat Fare</label>
                </b>
                <input
                  type="number"
                  className="form-control"
                  id="backSeatFare"
                  name="backSeatFare"
                  onChange={handleUserInput}
                  value={journey.backSeatFare}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <b>
                  <label className="form-label">Middle Seat Fare</label>
                </b>
                <input
                  type="number"
                  className="form-control"
                  id="middleSeatFare"
                  name="middleSeatFare"
                  onChange={handleUserInput}
                  value={journey.middleSeatFare}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <b>
                  <label className="form-label">First Class Seat Fare</label>
                </b>
                <input
                  type="number"
                  className="form-control"
                  id="frontSeatFare"
                  name="frontSeatFare"
                  onChange={handleUserInput}
                  value={journey.frontSeatFare}
                />
              </div>

              <div className="d-flex aligns-items-center justify-content-center">
                <input
                  type="submit"
                  className="btn text-color custom-bg"
                  value="Add Journey"
                />
              </div>
              <ToastContainer />
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddJourneyForm;
