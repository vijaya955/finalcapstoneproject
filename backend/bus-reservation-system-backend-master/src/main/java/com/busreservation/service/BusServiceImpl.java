package com.busreservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busreservation.dao.BusDao;
import com.busreservation.entity.Bus;

@Service
public class BusServiceImpl implements BusService {
	
	@Autowired
	private BusDao airplaneDao;

	@Override
	public Bus add(Bus airplane) {
		// TODO Auto-generated method stub
		return airplaneDao.save(airplane);
	}

	@Override
	public Bus getById(int id) {
		// TODO Auto-generated method stub
		return airplaneDao.findById(id).get();
	}

	@Override
	public List<Bus> getAll() {
		// TODO Auto-generated method stub
		return airplaneDao.findAll();
	}

}
