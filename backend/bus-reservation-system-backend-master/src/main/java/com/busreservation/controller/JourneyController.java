package com.busreservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busreservation.dto.CommonApiResponse;
import com.busreservation.dto.JourneyRequestDto;
import com.busreservation.dto.JourneyResponseDto;
import com.busreservation.dto.JourneyUpdateStatusRequestDto;
import com.busreservation.resource.JourneyResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/journey/")
@CrossOrigin(origins = "http://localhost:3000")
public class JourneyController {
	
	@Autowired
	private JourneyResource journeyResource;
	
	@PostMapping("add")
	@Operation(summary = "Api to add the Journey")
	public ResponseEntity<CommonApiResponse> addJourney(@RequestBody JourneyRequestDto journeyRequest) {
		return this.journeyResource.addJourney(journeyRequest);
	}
	
	@GetMapping("/fetch/all")
	@Operation(summary = "Api to fetch all journeys")
	public ResponseEntity<JourneyResponseDto> fetchAllJourneys() {
		return journeyResource.fetchAllJourneys();
	}
	
	@GetMapping("/search")
	@Operation(summary = "Api to search the journeys")
	public ResponseEntity<JourneyResponseDto> searchJourneys(@RequestParam("fromBusStopId") int fromAirportId, @RequestParam("endBusStopId") int endAirportId
			,@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
		return journeyResource.fetchAllJourneysByTimeRange(fromAirportId, endAirportId ,startTime, endTime);
	}
	
	@GetMapping("/status/all")
	@Operation(summary = "Api to fetch all journey status")
	public ResponseEntity<List<String>> fetchJourneys() {
		return journeyResource.fetchAllJourneyStatus();
	}
	
	@GetMapping("/class/all")
	@Operation(summary = "Api to fetch all journey class")
	public ResponseEntity<List<String>> fetchJourneyClass() {
		return journeyResource.fetchAllJourneyClass();
	}
	
	@PostMapping("update/status")
	@Operation(summary = "Api to update the Journey status")
	public ResponseEntity<CommonApiResponse> updateJourneyStatus(@RequestBody JourneyUpdateStatusRequestDto request) {
		return this.journeyResource.updateJourneyStatus(request);
	}
}
