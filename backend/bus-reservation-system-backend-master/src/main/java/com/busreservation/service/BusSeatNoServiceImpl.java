package com.busreservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busreservation.dao.BusSeatNoDao;
import com.busreservation.entity.Bus;
import com.busreservation.entity.BusSeatNo;

@Service
public class BusSeatNoServiceImpl implements BusSeatNoService {

	@Autowired
	private BusSeatNoDao busSeatNoDao;
	
	@Override
	public List<BusSeatNo> addAllSeat(List<BusSeatNo> seats) {
		// TODO Auto-generated method stub
		return busSeatNoDao.saveAll(seats);	
	}

	@Override
	public List<BusSeatNo> getByBus(Bus bus) {
		// TODO Auto-generated method stub
		return busSeatNoDao.findByBus(bus);
	}

}
