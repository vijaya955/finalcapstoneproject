package com.busreservation.dto;

import java.util.ArrayList;
import java.util.List;

import com.busreservation.entity.Bus;

import lombok.Data;

@Data
public class BusResponseDto extends CommonApiResponse {
	
	private List<Bus> buses = new ArrayList<>();

}
