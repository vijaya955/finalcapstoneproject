import { useState, useEffect } from "react";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer, toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const AddBusForm = () => {
  const navigate = useNavigate();

  const admin_token = sessionStorage.getItem("admin-jwtToken");

  const [bus, setBus] = useState({
    name: "",
    registrationNumber: "",
    totalSeat: "",
    description: "",
    backSeats: "",
    middleSeats: "",
    frontSeats: "",
  });

  const handleUserInput = (e) => {
    setBus({ ...bus, [e.target.name]: e.target.value });
  };

  const saveBus = (e) => {
    fetch("http://localhost:8080/api/bus/add", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: "Bearer " + admin_token,
      },
      body: JSON.stringify(bus),
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
              navigate("/admin/bus/all");
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
            <h5 className="card-title">Add Bus</h5>
          </div>
          <div className="card-body">
            <form className="row g-3" onSubmit={saveBus}>
              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Bus Name</b>
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  onChange={handleUserInput}
                  value={bus.name}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <b>
                  <label className="form-label">Bus Registration No.</label>
                </b>
                <input
                  type="text"
                  className="form-control"
                  id="registrationNumber"
                  name="registrationNumber"
                  onChange={handleUserInput}
                  value={bus.registrationNumber}
                />
              </div>

              <div class="mb-3 text-color">
                <label for="description" class="form-label">
                  <b>Bus Description</b>
                </label>
                <textarea
                  class="form-control"
                  id="description"
                  rows="3"
                  name="description"
                  placeholder="enter description.."
                  onChange={handleUserInput}
                  value={bus.description}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Total Seat</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="totalSeat"
                  name="totalSeat"
                  onChange={handleUserInput}
                  value={bus.totalSeat}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Total Back Seat</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="backSeats"
                  name="backSeats"
                  onChange={handleUserInput}
                  value={bus.backSeats}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Total Middle Seat</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="middleSeats"
                  name="middleSeats"
                  onChange={handleUserInput}
                  value={bus.middleSeats}
                />
              </div>

              <div className="col-md-6 mb-3 text-color">
                <label htmlFor="title" className="form-label">
                  <b>Total Front Seat</b>
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="frontSeats"
                  name="frontSeats"
                  onChange={handleUserInput}
                  value={bus.frontSeats}
                />
              </div>

              <div className="d-flex aligns-items-center justify-content-center">
                <input
                  type="submit"
                  className="btn bg-color custom-bg-text"
                  value="Add Bus"
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

export default AddBusForm;
