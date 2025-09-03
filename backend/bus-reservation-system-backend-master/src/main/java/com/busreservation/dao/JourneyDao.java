package com.busreservation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busreservation.entity.BusStop;
import com.busreservation.entity.Journey;

@Repository
public interface JourneyDao extends JpaRepository<Journey, Integer> {

	List<Journey> findByDepartureTimeGreaterThanEqualAndStatusNotIn(String departureTime, List<String> status);

	List<Journey> findByDepartureBusStopAndArrivalBusStopAndDepartureTimeBetweenAndStatusNotIn(
			BusStop departureBusStop, BusStop arrivalBusStop, String startTime, String endTime, List<String> status);

}
