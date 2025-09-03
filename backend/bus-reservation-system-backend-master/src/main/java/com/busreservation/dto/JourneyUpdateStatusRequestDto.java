package com.busreservation.dto;

import lombok.Data;

@Data
public class JourneyUpdateStatusRequestDto {
	
	private int journeyId;
	
	private String status;

}
