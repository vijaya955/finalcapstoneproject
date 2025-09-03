package com.busreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busreservation.dto.BusStopResponseDto;
import com.busreservation.dto.CommonApiResponse;
import com.busreservation.entity.BusStop;
import com.busreservation.resource.BusStopResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/busStop/")
@CrossOrigin(origins = "http://localhost:3000")
public class BusStopController {
	
	@Autowired
	private BusStopResource busStopResource;
	
	@PostMapping("add")
	@Operation(summary = "Api to add the BusStop")
	public ResponseEntity<CommonApiResponse> addBusStop(@RequestBody BusStop busStop) {
		return this.busStopResource.addBusStop(busStop);
	}
	
	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all busStops")
	public ResponseEntity<BusStopResponseDto> fetchAllBusStops() {
		return busStopResource.fetchAllBusStops();
	}

}
