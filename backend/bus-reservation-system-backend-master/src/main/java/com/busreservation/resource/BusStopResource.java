package com.busreservation.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.busreservation.dto.BusStopResponseDto;
import com.busreservation.dto.CommonApiResponse;
import com.busreservation.entity.BusStop;
import com.busreservation.service.BusStopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BusStopResource {

	private final Logger LOG = LoggerFactory.getLogger(BusStopResource.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private BusStopService busStopService;

	public ResponseEntity<CommonApiResponse> addBusStop(BusStop busStop) {

		LOG.info("Received request for add busStop");

		CommonApiResponse response = new CommonApiResponse();

		if (busStop == null) {
			response.setResponseMessage("missing data");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		BusStop addedBusStop = this.busStopService.add(busStop);

		if (addedBusStop == null) {
			response.setResponseMessage("Failed to add the BusStop");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.setResponseMessage("BusStop added successful");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<BusStopResponseDto> fetchAllBusStops() {

		BusStopResponseDto response = new BusStopResponseDto();

		List<BusStop> busStops = new ArrayList<>();

		busStops = this.busStopService.getAll();

		if (CollectionUtils.isEmpty(busStops)) {
			response.setResponseMessage("BusStop fetched success");
			response.setSuccess(true);

			return new ResponseEntity<BusStopResponseDto>(response, HttpStatus.OK);
		}

		response.setBusStops(busStops);
		response.setResponseMessage("BusStop fetched success");
		response.setSuccess(true);

		return new ResponseEntity<BusStopResponseDto>(response, HttpStatus.OK);

	}

}
