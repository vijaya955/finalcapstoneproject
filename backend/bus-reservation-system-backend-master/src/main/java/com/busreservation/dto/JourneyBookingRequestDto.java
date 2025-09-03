package com.busreservation.dto;

import lombok.Data;

@Data
public class JourneyBookingRequestDto {
	
    private int totalPassengers;
    
    private String journeyClassType;
    
    private int passengerId;

    private int journeyId;

}
