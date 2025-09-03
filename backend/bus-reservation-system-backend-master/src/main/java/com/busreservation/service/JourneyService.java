package com.busreservation.service;

import java.util.List;

import com.busreservation.entity.BusStop;
import com.busreservation.entity.Journey;

public interface JourneyService {

	Journey add(Journey journey);

	Journey getById(int id);

	List<Journey> getAll();

	List<Journey> getByDepartureBusStopAndArrivalBusStopAndDepartureTimeBetweenAndStatusNotIn(BusStop departureBusStop,
			BusStop arrivalBusStop, String startTime, String endTime, List<String> status);
	
	List<Journey> getByDepartureTimeGreaterThanEqualAndStatusNotIn(String departureTime, List<String> status);

}
