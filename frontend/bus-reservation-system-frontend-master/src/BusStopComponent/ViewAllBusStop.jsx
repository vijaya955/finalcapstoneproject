import { useState, useEffect } from "react";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";

const ViewAllBusStop = () => {
  const [allBusStops, setAllBusStops] = useState([]);

  const retrieveAllBusStop = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/busStop/fetch/all"
    );
    console.log(response.data);
    return response.data;
  };

  useEffect(() => {
    const getAllBusStops = async () => {
      const allBusStops = await retrieveAllBusStop();
      if (allBusStops) {
        setAllBusStops(allBusStops.busStops);
      }
    };

    getAllBusStops();
  }, []);

  return (
    <div>
      <div className="mt-2">
        <div
          className="card form-card ms-5 me-5 mb-5 border-color "
          style={{
            height: "30rem",
          }}
        >
          <div className="card-header custom-bg text-center text-color">
            <h2>All BusStop</h2>
          </div>
          <div
            className="card-body"
            style={{
              overflowY: "auto",
            }}
          >
            <div className="table-responsive">
              <table className="table table-hover text-color text-center">
                <thead className="table-bordered border-color text-color custom-bg">
                  <tr>
                    <th scope="col">BusStop</th>
                    <th scope="col">BusStop Location</th>
                    <th scope="col">BusStop Code</th>
                    <th scope="col">BusStop Address</th>
                  </tr>
                </thead>
                <tbody>
                  {allBusStops.map((busStop) => {
                    return (
                      <tr>
                        <td>
                          <b>{busStop.name}</b>
                        </td>
                        <td>
                          <b>{busStop.location}</b>
                        </td>
                        <td>
                          <b>{busStop.code}</b>
                        </td>
                        <td>
                          <b>{busStop.address}</b>
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
    </div>
  );
};

export default ViewAllBusStop;
