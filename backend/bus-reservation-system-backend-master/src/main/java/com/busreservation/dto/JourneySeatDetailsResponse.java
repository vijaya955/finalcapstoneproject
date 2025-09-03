package com.busreservation.dto;

import lombok.Data;

@Data
public class JourneySeatDetailsResponse extends CommonApiResponse {

	private int totalSeat;

	private int backSeats;

	private int middleSeats;

	private int frontSeats;

	private int backSeatsAvailable;

	private int middleSeatsAvailable;

	private int frontSeatsAvailable;
	
	private int backSeatsWaiting;

	private int middleSeatsWaiting;

	private int frontSeatsWaiting;

}
