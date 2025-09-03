import "./App.css";
import { Route, Routes } from "react-router-dom";
import AboutUs from "./page/AboutUs";
import ContactUs from "./page/ContactUs";
import Header from "./NavbarComponent/Header";
import HomePage from "./page/HomePage";
import UserRegister from "./UserComponent/UserRegister";
import UserLoginForm from "./UserComponent/UserLoginForm";
import AdminRegisterForm from "./UserComponent/AdminRegisterForm";
import ViewAllCustomers from "./UserComponent/ViewAllCustomers";
import AddBusForm from "./BusComponent/AddBusForm";
import AddBusStopForm from "./BusStopComponent/AddBusStopForm";
import ViewAllBus from "./BusComponent/ViewAllBus";
import ViewAllBusStop from "./BusStopComponent/ViewAllBusStop";
import AddJourneyForm from "./JourneyComponent/AddJourneyForm";
import ViewAllJourney from "./JourneyComponent/ViewAllJourney";
import UpdateJourneyStatusForm from "./JourneyComponent/UpdateJourneyStatusForm";
import BookJourney from "./JourneyBookingComponent/BookJourney";
import MyWallet from "./UserComponent/MyWallet";
import ViewAllJourneyBooking from "./JourneyBookingComponent/ViewAllJourneyBooking";
import ViewPassengerJourneyBooking from "./JourneyBookingComponent/ViewPassengerJourneyBooking";
import ViewScheduledJourneyBookings from "./JourneyBookingComponent/ViewScheduledJourneyBookings";

function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="contact" element={<ContactUs />} />
        <Route path="about" element={<AboutUs />} />
        <Route path="/user/passenger/register" element={<UserRegister />} />
        <Route path="/user/login" element={<UserLoginForm />} />
        <Route path="/user/admin/register" element={<AdminRegisterForm />} />
        <Route path="/admin/all/passenger" element={<ViewAllCustomers />} />
        <Route path="/admin/bus/add" element={<AddBusForm />} />
        <Route path="/admin/busStop/add" element={<AddBusStopForm />} />
        <Route path="/admin/bus/all" element={<ViewAllBus />} />
        <Route path="/admin/busStop/all" element={<ViewAllBusStop />} />
        <Route path="/admin/journey/add" element={<AddJourneyForm />} />
        <Route path="/admin/journey/all" element={<ViewAllJourney />} />
        <Route path="/view/journey/all" element={<ViewAllJourney />} />
        <Route
          path="/admin/journey/status/update"
          element={<UpdateJourneyStatusForm />}
        />
        <Route path="/passenger/journey/book" element={<BookJourney />} />
        <Route path="/passenger/wallet" element={<MyWallet />} />
        <Route
          path="/admin/journey/booking/all"
          element={<ViewAllJourneyBooking />}
        />

        <Route
          path="/passenger/journey/booking/all"
          element={<ViewPassengerJourneyBooking />}
        />

        <Route
          path="/journey/booking/status/"
          element={<ViewScheduledJourneyBookings />}
        />
      </Routes>
    </div>
  );
}

export default App;
