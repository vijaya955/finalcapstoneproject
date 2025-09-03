package com.busreservation.dto;

import java.util.ArrayList;
import java.util.List;

import com.busreservation.entity.Journey;
import com.busreservation.entity.JourneyBooking;

import lombok.Data;

@Data
public class JourneyResponseDto extends CommonApiResponse {
	
	private List<Journey> journeys = new ArrayList<>();
	
	private List<JourneyBooking> journeyBookings = new ArrayList<>();

}
