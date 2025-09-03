package com.busreservation.service;

import java.util.List;

import com.busreservation.entity.Bus;
import com.busreservation.entity.BusSeatNo;

public interface BusSeatNoService {
	
	List<BusSeatNo> addAllSeat(List<BusSeatNo> seats);
	List<BusSeatNo> getByBus(Bus airplane);
	

}
