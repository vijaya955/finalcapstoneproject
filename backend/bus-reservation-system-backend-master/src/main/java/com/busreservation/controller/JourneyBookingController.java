package com.busreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busreservation.dto.CommonApiResponse;
import com.busreservation.dto.JourneyBookingRequestDto;
import com.busreservation.dto.JourneyBookingResponseDto;
import com.busreservation.dto.JourneySeatDetailsResponse;
import com.busreservation.resource.JourneyBookingResource;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/journey/book")
@CrossOrigin(origins = "http://localhost:3000")
public class JourneyBookingController {

	@Autowired
	private JourneyBookingResource journeyBookingResource;

	@PostMapping("add")
	@Operation(summary = "Api to add the Journey Booking")
	public ResponseEntity<CommonApiResponse> addAirport(@RequestBody JourneyBookingRequestDto request) {
		return this.journeyBookingResource.addJourneyBooking(request);
	}

	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all journey bookings")
	public ResponseEntity<JourneyBookingResponseDto> fetchAllJourneys() {
		return this.journeyBookingResource.fetchAllJourneyBookings();
	}

	@GetMapping("/fetch")
	@Operation(summary = "Api to fetch journey bookings by journey id")
	public ResponseEntity<JourneyBookingResponseDto> fetchAllByJourney(@RequestParam("journeyId") int journeyId) {
		return this.journeyBookingResource.fetchBookingsByJourney(journeyId);
	}

	@GetMapping("/fetch/user")
	@Operation(summary = "Api to fetch user journey bookings")
	public ResponseEntity<JourneyBookingResponseDto> fetchUserBookings(@RequestParam("userId") int userId) {
		return this.journeyBookingResource.fetchUserBookings(userId);
	}

	@DeleteMapping("/ticket/cancel")
	@Operation(summary = "Api to cancel the ticket using booking Id")
	public ResponseEntity<CommonApiResponse> cancelTrainTicket(@RequestParam("bookingId") int bookingId) {
		return this.journeyBookingResource.cancelJourneyBooking(bookingId);
	}

	@GetMapping("/download/ticket")
	@Operation(summary = "Api to download the ticket by Booking Ticket")
	public void downloadBill(@RequestParam("bookingId") String bookingId, HttpServletResponse response)
			throws Exception {
		this.journeyBookingResource.downloadBookingTicket(bookingId, response);
	}
	
	@GetMapping("/fetch/seatDetails")
	@Operation(summary = "Api to fetch journey seat details")
	public ResponseEntity<JourneySeatDetailsResponse> fetchJourneySeatDetails(@RequestParam("journeyId") int journeyId) {
		return this.journeyBookingResource.fetchJourneySeatDetails(journeyId);
	}

}