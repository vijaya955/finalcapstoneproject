package com.busreservation.service;

import java.util.List;

import com.busreservation.entity.Journey;
import com.busreservation.entity.JourneyBooking;
import com.busreservation.entity.User;

public interface JourneyBookingService {
	
	JourneyBooking add(JourneyBooking journeyBooking);
	JourneyBooking getById(int id);
	List<JourneyBooking> getAll();
	List<JourneyBooking> getByPassenger(User user);
	List<JourneyBooking> getByJourney(Journey journey);
	List<JourneyBooking> getByJourneyAndStatus(Journey journey, String status);
	List<JourneyBooking> getByJourneyAndStatusNotIn(Journey journey, List<String> status);
	List<JourneyBooking> getByJourneyAndStatusAndJourneyClass(Journey journey, String status, String journeyClass);
	List<JourneyBooking> getByJourneyAndStatusNotInAndJourneyClass(Journey journey, List<String> status, String journeyClass);
	List<JourneyBooking> getByJourneyAndJourneyClass(Journey journey, String journeyClass);
	List<JourneyBooking> getByIdAndStatus(int id, String status);
	List<JourneyBooking> getByStatusIn(List<String> statuses);
	List<JourneyBooking> getByBookingId(String bookingId);


}
