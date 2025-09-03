package com.busreservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busreservation.dao.JourneyDao;
import com.busreservation.entity.BusStop;
import com.busreservation.entity.Journey;

@Service
public class JourneyServiceImpl implements JourneyService {
	
	@Autowired
	private JourneyDao journeyDao;

	@Override
	public Journey add(Journey journey) {
		// TODO Auto-generated method stub
		return journeyDao.save(journey);
	}

	@Override
	public Journey getById(int id) {
		// TODO Auto-generated method stub
		return journeyDao.findById(id).get();
	}

	@Override
	public List<Journey> getAll() {
		// TODO Auto-generated method stub
		return journeyDao.findAll();
	}

	@Override
	public List<Journey> getByDepartureBusStopAndArrivalBusStopAndDepartureTimeBetweenAndStatusNotIn(
			BusStop departureBusStop, BusStop arrivalBusStop, String startTime, String endTime, List<String> status) {
		// TODO Auto-generated method stub
		return journeyDao.findByDepartureBusStopAndArrivalBusStopAndDepartureTimeBetweenAndStatusNotIn(departureBusStop, arrivalBusStop, startTime, endTime, status);
	}

	@Override
	public List<Journey> getByDepartureTimeGreaterThanEqualAndStatusNotIn(String departureTime, List<String> status) {
		// TODO Auto-generated method stub
		return journeyDao.findByDepartureTimeGreaterThanEqualAndStatusNotIn(departureTime, status);
	}

	

}
