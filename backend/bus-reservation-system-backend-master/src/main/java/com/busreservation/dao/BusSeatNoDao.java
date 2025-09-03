package com.busreservation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busreservation.entity.Bus;
import com.busreservation.entity.BusSeatNo;

@Repository
public interface BusSeatNoDao extends JpaRepository<BusSeatNo, Integer> {
	
	List<BusSeatNo> findByBus(Bus bus);

}
