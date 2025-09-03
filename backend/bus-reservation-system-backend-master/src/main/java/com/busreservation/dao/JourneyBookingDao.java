package com.busreservation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busreservation.entity.Journey;
import com.busreservation.entity.JourneyBooking;
import com.busreservation.entity.User;

@Repository
public interface JourneyBookingDao extends JpaRepository<JourneyBooking, Integer> {
	
	List<JourneyBooking> findByPassenger(User user);
	List<JourneyBooking> findByJourney(Journey journey);
	List<JourneyBooking> findByJourneyAndStatus(Journey journey, String status);
	List<JourneyBooking> findByJourneyAndStatusNotIn(Journey journey, List<String> status);
	List<JourneyBooking> findByJourneyAndStatusAndJourneyClass(Journey journey, String status, String journeyClass);
	List<JourneyBooking> findByJourneyAndStatusNotInAndJourneyClass(Journey journey, List<String> status, String journeyClass);
	List<JourneyBooking> findByJourneyAndJourneyClass(Journey journey, String journeyClass);
	List<JourneyBooking> findByIdAndStatus(int id, String status);
	List<JourneyBooking> findByStatusIn(List<String> statuses);
	List<JourneyBooking> findByBookingId(String bookingId);

}
