package com.busreservation.dto;

import java.util.ArrayList;
import java.util.List;

import com.busreservation.entity.JourneyBooking;

import lombok.Data;

@Data
public class JourneyBookingResponseDto extends CommonApiResponse {
	
	private List<JourneyBooking> bookings = new ArrayList<>();

}
