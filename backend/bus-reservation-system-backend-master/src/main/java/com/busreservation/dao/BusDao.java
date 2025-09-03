package com.busreservation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busreservation.entity.Bus;

@Repository
public interface BusDao extends JpaRepository<Bus, Integer> {

}
