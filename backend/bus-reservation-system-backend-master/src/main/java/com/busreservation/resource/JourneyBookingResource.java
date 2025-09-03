package com.busreservation.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.busreservation.dto.CommonApiResponse;
import com.busreservation.dto.JourneyBookingRequestDto;
import com.busreservation.dto.JourneyBookingResponseDto;
import com.busreservation.dto.JourneySeatDetailsResponse;
import com.busreservation.entity.Journey;
import com.busreservation.entity.JourneyBooking;
import com.busreservation.entity.User;
import com.busreservation.service.JourneyBookingService;
import com.busreservation.service.JourneyService;
import com.busreservation.service.UserService;
import com.busreservation.utility.Constants.JourneyBookingStatus;
import com.busreservation.utility.Constants.JourneyClassType;
import com.busreservation.utility.IdGenerator;
import com.busreservation.utility.TicketDownloader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class JourneyBookingResource {

	private final Logger LOG = LoggerFactory.getLogger(JourneyBookingResource.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private JourneyBookingService journeyBookingService;

	@Autowired
	private JourneyService journeyService;

	@Autowired
	private UserService userService;

	public ResponseEntity<CommonApiResponse> addJourneyBooking(JourneyBookingRequestDto request) {

		LOG.info("Received request for add journey");

		CommonApiResponse response = new CommonApiResponse();

		if (request.getJourneyClassType() == null || request.getTotalPassengers() == 0
				|| request.getJourneyId() == 0 | request.getPassengerId() == 0) {

			response.setResponseMessage("missing data");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Journey journey = this.journeyService.getById(request.getJourneyId());

		User passenger = this.userService.getUserById(request.getPassengerId());

		String bookingTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

//		List<JourneyBooking> totalJourneyBookings = this.journeyBookingService.getByJourney(journey);

		List<JourneyBooking> totalJourneyBookings = new ArrayList<>();

		String bookingId = IdGenerator.generateBookingId();

		List<JourneyBooking> totalAvailableBookings = new ArrayList<>();
		List<JourneyBooking> totalWaitingBookings = new ArrayList<>();

		int totalAvailableSeat;
		int totalWaitingSeat;

		if (request.getJourneyClassType().equals(JourneyClassType.BACK.value())) {

			BigDecimal totalFare = journey.getBackSeatFare()
					.multiply(BigDecimal.valueOf(request.getTotalPassengers()));

			if (passenger.getWalletAmount().compareTo(totalFare) < 0) {
				response.setResponseMessage("Insufficient Funds in Passenger Wallet");
				response.setSuccess(true);

				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}

			totalJourneyBookings = this.journeyBookingService.getByJourneyAndJourneyClass(journey,
					JourneyClassType.BACK.value());

		}

		else if (request.getJourneyClassType().equals(JourneyClassType.MIDDLE.value())) {

			BigDecimal totalFare = journey.getMiddleSeatFare()
					.multiply(BigDecimal.valueOf(request.getTotalPassengers()));

			if (passenger.getWalletAmount().compareTo(totalFare) < 0) {
				response.setResponseMessage("Insufficient Funds in Passenger Wallet");
				response.setSuccess(true);

				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}

			totalJourneyBookings = this.journeyBookingService.getByJourneyAndJourneyClass(journey,
					JourneyClassType.MIDDLE.value());

		}

		else if (request.getJourneyClassType().equals(JourneyClassType.FRONT.value())) {

			BigDecimal totalFare = journey.getFrontSeatFare()
					.multiply(BigDecimal.valueOf(request.getTotalPassengers()));

			if (passenger.getWalletAmount().compareTo(totalFare) < 0) {
				response.setResponseMessage("Insufficient Funds in Passenger Wallet");
				response.setSuccess(true);

				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}

			totalJourneyBookings = this.journeyBookingService.getByJourneyAndJourneyClass(journey,
					JourneyClassType.FRONT.value());

		}

		for (JourneyBooking journeyBooking : totalJourneyBookings) {

			if (journeyBooking.getStatus().equals(JourneyBookingStatus.AVAILABLE.value())) {
				totalAvailableBookings.add(journeyBooking);
				continue;
			}

			if (journeyBooking.getStatus().equals(JourneyBookingStatus.WAITING.value())) {
				totalWaitingBookings.add(journeyBooking);
				continue;
			}

		}

		totalAvailableSeat = totalAvailableBookings.size();
		totalWaitingSeat = totalWaitingBookings.size();

		// all the seats are available
		if (request.getTotalPassengers() <= totalAvailableSeat) {

			for (int i = 0; i < request.getTotalPassengers(); i++) {
				JourneyBooking booking = totalAvailableBookings.get(i);
				booking.setBookingId(bookingId);
				booking.setBookingTime(bookingTime);
				booking.setStatus(JourneyBookingStatus.CONFIRMED.value());
				booking.setPassenger(passenger);
				this.journeyBookingService.add(booking);

				if (request.getJourneyClassType().equals(JourneyClassType.BACK.value())) {
					passenger.setWalletAmount(passenger.getWalletAmount().subtract(journey.getBackSeatFare()));

				} else if (request.getJourneyClassType().equals(JourneyClassType.MIDDLE.value())) {
					passenger.setWalletAmount(passenger.getWalletAmount().subtract(journey.getMiddleSeatFare()));

				} else if (request.getJourneyClassType().equals(JourneyClassType.FRONT.value())) {
					passenger.setWalletAmount(passenger.getWalletAmount().subtract(journey.getFrontSeatFare()));

				}

				this.userService.registerUser(passenger);

			}

		}

		// all seats are not available
		else {

			// that means already some users booking is in waiting, so this bookings will
			// also be in waiting no doubt
			if (totalWaitingSeat > 0) {

				for (int i = 0; i < request.getTotalPassengers(); i++) {
					JourneyBooking existingBooking = totalJourneyBookings.get(i); // for adding new entry in
																				// WAITING

					JourneyBooking bookingInWaiting = JourneyBooking.builder().journey(journey)
							.journeyClass(existingBooking.getJourneyClass()).passenger(passenger).bookingId(bookingId)
							.bookingTime(bookingTime).status(JourneyBookingStatus.WAITING.value()).build();
					this.journeyBookingService.add(bookingInWaiting);
				}

			}

			// now here we can clearly say that total seat which we require is NOT FULLY
			// AVAILABLE
			// some seat are available and some will have to be in WAITING
			else {

				int totalSeatsToAddInWaiting = request.getTotalPassengers() - totalAvailableSeat;

				// firstly confirm all the available seats
				for (JourneyBooking booking : totalAvailableBookings) {
					booking.setBookingId(bookingId);
					booking.setBookingTime(bookingTime);
					booking.setStatus(JourneyBookingStatus.CONFIRMED.value());
					booking.setPassenger(passenger);
					this.journeyBookingService.add(booking);

					if (request.getJourneyClassType().equals(JourneyClassType.BACK.value())) {
						passenger.setWalletAmount(passenger.getWalletAmount().subtract(journey.getBackSeatFare()));

					} else if (request.getJourneyClassType().equals(JourneyClassType.MIDDLE.value())) {
						passenger.setWalletAmount(passenger.getWalletAmount().subtract(journey.getMiddleSeatFare()));

					} else if (request.getJourneyClassType().equals(JourneyClassType.FRONT.value())) {
						passenger.setWalletAmount(passenger.getWalletAmount().subtract(journey.getFrontSeatFare()));

					}

					this.userService.registerUser(passenger);

				}

				// secondly add pending seats as waiting
				for (int i = 0; i < totalSeatsToAddInWaiting; i++) {
					JourneyBooking existingBooking = totalJourneyBookings.get(i); // for adding new entry in
					// WAITING

					JourneyBooking bookingInWaiting = JourneyBooking.builder().journey(journey)
							.journeyClass(existingBooking.getJourneyClass()).passenger(passenger).bookingId(bookingId)
							.bookingTime(bookingTime).status(JourneyBookingStatus.WAITING.value()).build();

					this.journeyBookingService.add(bookingInWaiting);
				}

			}

		}

		response.setResponseMessage(
				"Your Booking ID is [ " + bookingId + " ] , Please check the booking status in dashboard");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);

			LOG.info(jsonString);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<JourneyBookingResponseDto> fetchAllJourneyBookings() {

		LOG.info("Received request for fetching all bookings");

		JourneyBookingResponseDto response = new JourneyBookingResponseDto();

		List<JourneyBooking> allBookings = new ArrayList<>();

		allBookings = this.journeyBookingService.getByStatusIn(Arrays.asList(JourneyBookingStatus.CONFIRMED.value(),
				JourneyBookingStatus.CANCELLED.value(), JourneyBookingStatus.WAITING.value()));

		if (CollectionUtils.isEmpty(allBookings)) {
			response.setResponseMessage("Failed to book the seats");
			response.setSuccess(true);

			return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(allBookings);
		response.setResponseMessage("Failed to book the seats");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);

			LOG.info(jsonString);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<JourneyBookingResponseDto> fetchBookingsByJourney(int journeyId) {

		LOG.info("Received request for fetching all bookings by using journey id");

		JourneyBookingResponseDto response = new JourneyBookingResponseDto();

		if (journeyId == 0) {
			response.setResponseMessage("missing data");
			response.setSuccess(true);

			return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<JourneyBooking> allBookings = new ArrayList<>();

		Journey journey = this.journeyService.getById(journeyId);

		// all bookings with Available & Confirmed
		allBookings = this.journeyBookingService.getByJourneyAndStatusNotIn(journey,
				Arrays.asList(JourneyBookingStatus.CANCELLED.value(), JourneyBookingStatus.WAITING.value(),
						JourneyBookingStatus.PENDING.value()));

		if (CollectionUtils.isEmpty(allBookings)) {
			response.setResponseMessage("Failed to book the seats");
			response.setSuccess(true);

			return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(allBookings);
		response.setResponseMessage("Failed to book the seats");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);

			LOG.info(jsonString);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<JourneyBookingResponseDto> fetchUserBookings(int userId) {

		LOG.info("Received request for fetching user bookings");

		JourneyBookingResponseDto response = new JourneyBookingResponseDto();

		if (userId == 0) {
			response.setResponseMessage("missing data");
			response.setSuccess(true);

			return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User passenger = this.userService.getUserById(userId);

		List<JourneyBooking> allBookings = new ArrayList<>();

		allBookings = this.journeyBookingService.getByPassenger(passenger);

		if (CollectionUtils.isEmpty(allBookings)) {
			response.setResponseMessage("Failed to book the seats");
			response.setSuccess(true);

			return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(allBookings);
		response.setResponseMessage("Failed to book the seats");
		response.setSuccess(true);

		return new ResponseEntity<JourneyBookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> cancelJourneyBooking(int bookingId) {

		CommonApiResponse response = new CommonApiResponse();

		if (bookingId == 0) {
			response.setResponseMessage("Failed to Cancel Booking, missing booking id");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		JourneyBooking booking = this.journeyBookingService.getById(bookingId);

		if (booking == null) {
			response.setResponseMessage("No Booking found, failed to cancel booking");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getStatus().equals(JourneyBookingStatus.CANCELLED.value())) {
			response.setResponseMessage("Ticket is already cancelled");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getStatus().equals(JourneyBookingStatus.WAITING.value())) {

			booking.setStatus(JourneyBookingStatus.CANCELLED.value());
			journeyBookingService.add(booking);

			response.setResponseMessage("Ticket Cancelled Successfully");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}

		if (booking.getStatus().equals(JourneyBookingStatus.CONFIRMED.value())) {

			booking.setStatus(JourneyBookingStatus.CANCELLED.value());
			journeyBookingService.add(booking);
			
			User customer = booking.getPassenger();
			
			String busSeatNo = booking.getBusSeatNo().getSeatNo();
			
			BigDecimal amountToRefund = BigDecimal.ZERO;
			
			if(busSeatNo.contains("B-")) {
				amountToRefund = booking.getJourney().getBackSeatFare();
			} else if(busSeatNo.contains("M-")) {
				amountToRefund = booking.getJourney().getMiddleSeatFare();
			} else if(busSeatNo.contains("F-")) {
				amountToRefund = booking.getJourney().getFrontSeatFare();
			}
			
			
            BigDecimal walletBalance = customer.getWalletAmount().add(amountToRefund);
			customer.setWalletAmount(walletBalance);
			
			User updatedCustomer = this.userService.updateUser(customer);
            
			// if waiting seat already present, then try to make that confirmed
			List<JourneyBooking> waitingBookings = this.journeyBookingService.getByJourneyAndStatusNotInAndJourneyClass(
					booking.getJourney(),
					Arrays.asList(JourneyBookingStatus.AVAILABLE.value(), JourneyBookingStatus.CANCELLED.value(),
							JourneyBookingStatus.CONFIRMED.value(), JourneyBookingStatus.PENDING.value()),
					booking.getJourneyClass());

			if (!CollectionUtils.isEmpty(waitingBookings)) {

				boolean addNewAvailableSeat = true;

				for (JourneyBooking waitingBooking : waitingBookings) {

					User user = waitingBooking.getPassenger();

					if (booking.getJourneyClass().equals(JourneyClassType.BACK.value())) {
						// amount present in wallet
						if (user.getWalletAmount().compareTo(booking.getJourney().getBackSeatFare()) > 0) {
							BigDecimal amountToDebit = booking.getJourney().getBackSeatFare();
							
							BigDecimal newBalance = user.getWalletAmount().subtract(amountToDebit);
							
							updatedCustomer.setWalletAmount(newBalance);
				            this.userService.updateUser(updatedCustomer);
							
							waitingBooking.setBusSeatNo(booking.getBusSeatNo());
							waitingBooking.setStatus(JourneyBookingStatus.CONFIRMED.value());
							this.journeyBookingService.add(booking);

							addNewAvailableSeat = false;

							break;
						}

					} else if (booking.getJourneyClass().equals(JourneyClassType.MIDDLE.value())) {

						// amount present in wallet
						if (user.getWalletAmount().compareTo(booking.getJourney().getMiddleSeatFare()) > 0) {
                            BigDecimal amountToDebit = booking.getJourney().getMiddleSeatFare();
							
							BigDecimal newBalance = user.getWalletAmount().subtract(amountToDebit);
							
							updatedCustomer.setWalletAmount(newBalance);
				            this.userService.updateUser(updatedCustomer);
							
							waitingBooking.setBusSeatNo(booking.getBusSeatNo());
							waitingBooking.setStatus(JourneyBookingStatus.CONFIRMED.value());
							this.journeyBookingService.add(booking);

							addNewAvailableSeat = false;

							break;
						}

					} else if (booking.getJourneyClass().equals(JourneyClassType.FRONT.value())) {

						// amount present in wallet
						if (user.getWalletAmount().compareTo(booking.getJourney().getFrontSeatFare()) > 0) {
							
                            BigDecimal amountToDebit = booking.getJourney().getMiddleSeatFare();
							
							BigDecimal newBalance = user.getWalletAmount().subtract(amountToDebit);
							
							updatedCustomer.setWalletAmount(newBalance);
				            this.userService.updateUser(updatedCustomer);
							
							waitingBooking.setBusSeatNo(booking.getBusSeatNo());
							waitingBooking.setStatus(JourneyBookingStatus.CONFIRMED.value());
							this.journeyBookingService.add(booking);

							addNewAvailableSeat = false;

							break;
						}
					}

				}

				if (addNewAvailableSeat) {
					// now making new ticket available to customer with the same seat
					JourneyBooking ticketToMakeAvailable = JourneyBooking.builder().journey(booking.getJourney())
							.status(JourneyBookingStatus.AVAILABLE.value()).busSeatNo(booking.getBusSeatNo())
							.journeyClass(booking.getJourneyClass()).build();

					journeyBookingService.add(ticketToMakeAvailable);

				}

			}

			// if waiting seat is not there, then add new Available seat with same seat
			else {

				// now making new ticket available to customer with the same seat
				JourneyBooking ticketToMakeAvailable = JourneyBooking.builder().journey(booking.getJourney())
						.status(JourneyBookingStatus.AVAILABLE.value()).busSeatNo(booking.getBusSeatNo())
						.journeyClass(booking.getJourneyClass()).build();

				journeyBookingService.add(ticketToMakeAvailable);

			}

			response.setResponseMessage("Ticket Cancelled Successfully");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
		}
		return null;

	}

	public void downloadBookingTicket(String bookingId, HttpServletResponse response)
			throws DocumentException, IOException {

		if (bookingId == null) {
			return;
		}

		List<JourneyBooking> bookings = this.journeyBookingService.getByBookingId(bookingId);

		if (bookings == null) {
			return;
		}

		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + bookings.get(0).getPassenger().getName() + " "
				+ bookings.get(0).getBookingId() + "_journey_ticket.pdf";
		response.setHeader(headerKey, headerValue);

		TicketDownloader exporter = new TicketDownloader(bookings);
		exporter.export(response);

		return;

	}

	public ResponseEntity<JourneySeatDetailsResponse> fetchJourneySeatDetails(int journeyId) {

		JourneySeatDetailsResponse response = new JourneySeatDetailsResponse();

		if (journeyId == 0) {
			response.setResponseMessage("missing data");
			response.setSuccess(true);

			return new ResponseEntity<JourneySeatDetailsResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Journey journey = this.journeyService.getById(journeyId);

		List<JourneyBooking> bookings = new ArrayList<>();

		bookings = this.journeyBookingService.getByJourney(journey);

		if (CollectionUtils.isEmpty(bookings)) {

			response.setResponseMessage("bookings not found");
			response.setSuccess(true);

			return new ResponseEntity<JourneySeatDetailsResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		List<JourneyBooking> economyAvailable = new ArrayList<>();
		List<JourneyBooking> economyWaiting = new ArrayList<>();

		List<JourneyBooking> businessAvailable = new ArrayList<>();
		List<JourneyBooking> businessWaiting = new ArrayList<>();

		List<JourneyBooking> firstClassAvailable = new ArrayList<>();
		List<JourneyBooking> firstClassWaiting = new ArrayList<>();

		for (JourneyBooking booking : bookings) {

			if (booking.getJourneyClass().equals(JourneyClassType.BACK.value())
					&& booking.getStatus().equals(JourneyBookingStatus.AVAILABLE.value())) {
				economyAvailable.add(booking);
			}

			else if (booking.getJourneyClass().equals(JourneyClassType.BACK.value())
					&& booking.getStatus().equals(JourneyBookingStatus.WAITING.value())) {
				economyWaiting.add(booking);
			}

			if (booking.getJourneyClass().equals(JourneyClassType.MIDDLE.value())
					&& booking.getStatus().equals(JourneyBookingStatus.AVAILABLE.value())) {
				businessAvailable.add(booking);
			}

			else if (booking.getJourneyClass().equals(JourneyClassType.MIDDLE.value())
					&& booking.getStatus().equals(JourneyBookingStatus.WAITING.value())) {
				businessWaiting.add(booking);
			}

			if (booking.getJourneyClass().equals(JourneyClassType.FRONT.value())
					&& booking.getStatus().equals(JourneyBookingStatus.AVAILABLE.value())) {
				firstClassAvailable.add(booking);
			}

			else if (booking.getJourneyClass().equals(JourneyClassType.FRONT.value())
					&& booking.getStatus().equals(JourneyBookingStatus.WAITING.value())) {
				firstClassWaiting.add(booking);
			}

		}

		response.setMiddleSeats(journey.getBus().getMiddleSeats());
		response.setBackSeats(journey.getBus().getBackSeats());
		response.setFrontSeats(journey.getBus().getFrontSeats());

		response.setFrontSeatsAvailable(firstClassAvailable.size());
		response.setMiddleSeatsAvailable(businessAvailable.size());
		response.setBackSeatsAvailable(economyAvailable.size());

		response.setBackSeatsWaiting(economyWaiting.size());
		response.setMiddleSeatsWaiting(businessWaiting.size());
		response.setFrontSeatsWaiting(firstClassWaiting.size());

		response.setTotalSeat(journey.getBus().getTotalSeat());

		response.setResponseMessage("journey seats fetch successful");
		response.setSuccess(true);

		return new ResponseEntity<JourneySeatDetailsResponse>(response, HttpStatus.OK);
	}

}
