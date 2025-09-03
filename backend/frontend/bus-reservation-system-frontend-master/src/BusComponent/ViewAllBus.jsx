import { useState, useEffect } from "react";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";

const ViewAllBus = () => {
  const [allBuss, setAllBuss] = useState([]);

  const admin_token = sessionStorage.getItem("admin-jwtToken");

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

  useEffect(() => {
    const getAllBuss = async () => {
      const allBuss = await retrieveAllBuss();
      if (allBuss) {
        setAllBuss(allBuss.buses);
      }
    };

    getAllBuss();
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
            <h2>All Bus</h2>
          </div>
          <div
            className="card-body"
            style={{
              overflowY: "auto",
            }}
          >
            <div className="table-responsive">
              <table className="table table-hover text-color text-center">
                <thead className="table-bordered border-color bg-color text-color">
                  <tr>
                    <th scope="col">Bus</th>
                    <th scope="col">Registration Number</th>
                    <th scope="col">Bus Description</th>
                    <th scope="col">Total Seat</th>
                    <th scope="col">Total Back Seat</th>
                    <th scope="col">Total Middle Seat</th>
                    <th scope="col">Total Front Seat</th>
                  </tr>
                </thead>
                <tbody>
                  {allBuss.map((bus) => {
                    return (
                      <tr>
                        <td>
                          <b>{bus.name}</b>
                        </td>

                        <td>
                          <b>{bus.registrationNumber}</b>
                        </td>
                        <td>
                          <b>{bus.description}</b>
                        </td>
                        <td>
                          <b>{bus.totalSeat}</b>
                        </td>
                        <td>
                          <b>{bus.backSeats}</b>
                        </td>
                        <td>
                          <b>{bus.middleSeats}</b>
                        </td>
                        <td>
                          <b>{bus.frontSeats}</b>
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

export default ViewAllBus;
