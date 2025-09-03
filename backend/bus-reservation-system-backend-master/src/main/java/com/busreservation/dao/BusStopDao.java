package com.busreservation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busreservation.entity.BusStop;

@Repository
public interface BusStopDao  extends JpaRepository<BusStop, Integer>{

}
