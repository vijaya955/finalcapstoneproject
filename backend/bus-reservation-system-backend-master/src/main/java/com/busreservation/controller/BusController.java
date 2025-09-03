package com.busreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busreservation.dto.BusResponseDto;
import com.busreservation.dto.CommonApiResponse;
import com.busreservation.entity.Bus;
import com.busreservation.resource.BusResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/bus/")
@CrossOrigin(origins = "http://localhost:3000")
public class BusController {
	
	@Autowired
	private BusResource busResource;
	
	@PostMapping("add")
	@Operation(summary = "Api to add the Bus")
	public ResponseEntity<CommonApiResponse> addBus(@RequestBody Bus bus) {
		return this.busResource.addBus(bus);
	}
	
	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all bus")
	public ResponseEntity<BusResponseDto> fetchAllBus() {
		return this.busResource.fetchAllBus();
	}

}
