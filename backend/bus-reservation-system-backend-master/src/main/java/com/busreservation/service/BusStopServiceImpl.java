package com.busreservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busreservation.dao.BusStopDao;
import com.busreservation.entity.BusStop;

@Service
public class BusStopServiceImpl implements BusStopService {

	@Autowired
	private BusStopDao airportDao;
	
	@Override
	public BusStop add(BusStop airport) {
		// TODO Auto-generated method stub
		return airportDao.save(airport);
	}

	@Override
	public BusStop getById(int id) {
		// TODO Auto-generated method stub
		return airportDao.findById(id).get();
	}

	@Override
	public List<BusStop> getAll() {
		// TODO Auto-generated method stub
		return airportDao.findAll();
	}

}
