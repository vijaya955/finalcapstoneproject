package com.busreservation.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class JourneyRequestDto {
	
	private String departureTime;
	
	private String arrivalTime;
	
	private String status; // Scheduled, On Time, Delayed, etc.

	private int departureBusStopId;

	private int arrivalBusStopId;

	private int busId;
	
    private BigDecimal backSeatFare;
	
	private BigDecimal middleSeatFare;
	
	private BigDecimal frontSeatFare;

}
