# Bus Reservation System — Frontend

A React-based frontend for a Bus Reservation System that supports two roles: ADMIN and PASSENGER. Admins can manage buses, stops, and journeys; passengers can register, log in, book journeys, and manage their wallet.

## Tech Stack
- **React 18** with **Create React App**
- **React Router v6** for client-side routing
- **Bootstrap 5** for UI components
- **React Toastify** for notifications
- **Fetch API** (and axios dependency available) for HTTP calls

## Features
- **Authentication**: Login for Admin and Passenger (JWT persisted in `sessionStorage`).
- **Role-based navigation**: Separate headers and pages per role.
- **Admin**:
  - Add bus, add bus stop
  - View all buses and bus stops
  - Add journey, update journey status
  - View all journey bookings
- **Passenger**:
  - Register and login
  - Book journeys, view own bookings
  - Wallet view
- **Common pages**: Home, About Us, Contact Us

## Prerequisites
- **Node.js** v16+ and **npm**
- Backend API running (default assumed at `http://localhost:8080`)

## Getting Started
1. **Install dependencies**
   ```bash
   npm install
   ```
2. **Configure environment (optional but recommended)**
   Create a `.env` file at project root:
   ```ini
   REACT_APP_API_BASE_URL=http://localhost:8080
   ```
   Note: Some components currently call `http://localhost:8080/...` directly via `fetch`. You can centralize base URL usage by refactoring to use `process.env.REACT_APP_API_BASE_URL` (or axios with a pre-configured base URL).
3. **Start the app**
   ```bash
   npm start
   ```
   App runs at: http://localhost:3000

## Available Scripts
- `npm start` — Run in development mode
- `npm run build` — Production build to `build/`
- `npm test` — Run tests (Jest + React Testing Library)
- `npm run eject` — Eject CRA configuration (irreversible)

## Routing Overview
Defined in `src/App.js`:
- `/` and `/home` — Home page
- `/about`, `/contact` — Static pages
- `/user/passenger/register` — Passenger registration
- `/user/login` — Login (Admin/Passenger)
- `/user/admin/register` — Admin registration
- `/admin/all/passenger` — View all passengers (admin)
- `/admin/bus/add`, `/admin/bus/all` — Manage buses (admin)
- `/admin/busStop/add`, `/admin/busStop/all` — Manage bus stops (admin)
- `/admin/journey/add`, `/admin/journey/all` — Manage journeys (admin)
- `/admin/journey/status/update` — Update journey status (admin)
- `/admin/journey/booking/all` — View all bookings (admin)
- `/passenger/journey/book` — Book a journey (passenger)
- `/passenger/journey/booking/all` — View passenger bookings
- `/journey/booking/status/` — View scheduled bookings
- `/passenger/wallet` — Wallet page

## API & Storage Notes
- Default API endpoint in some components: `http://localhost:8080`
- Auth data persisted in `sessionStorage` keys (examples seen):
  - `admin-jwtToken`, `passenger-jwtToken`
  - `active-admin`, `active-passenger`

## Styling
- Global theming in `src/index.css` with CSS variables
- Bootstrap 5 loaded via CDN in `public/index.html`

## Project Structure (key parts)
```
src/
├─ App.js
├─ index.js
├─ index.css
├─ NavbarComponent/
├─ UserComponent/
├─ BusComponent/
├─ BusStopComponent/
├─ JourneyComponent/
└─ JourneyBookingComponent/
```

## Build & Deploy
- Build the production bundle:
  ```bash
  npm run build
  ```
- Serve the `build/` directory with any static file server or integrate with your backend.

## Contributing
1. Create a feature branch
2. Commit with clear messages
3. Open a Pull Request

## License
Specify your license here (e.g., MIT). Update this section if your project uses a different license.
