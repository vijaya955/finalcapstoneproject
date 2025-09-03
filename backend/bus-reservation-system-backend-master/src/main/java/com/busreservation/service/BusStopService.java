package com.busreservation.service;

import java.util.List;

import com.busreservation.entity.BusStop;

public interface BusStopService {
	
	BusStop add(BusStop airport);
	BusStop getById(int id);
	List<BusStop> getAll();

}
