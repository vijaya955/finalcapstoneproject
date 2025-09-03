package com.busreservation.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Journey {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String journeyNumber;
	
	private String departureTime;
	
	private String arrivalTime;
	
	private String status; // Scheduled, On Time, Delayed, etc.

	@ManyToOne
	@JoinColumn(name = "departure_busstop_id")
	private BusStop departureBusStop;

	@ManyToOne
	@JoinColumn(name = "arrival_busstop_id")
	private BusStop arrivalBusStop;

	@ManyToOne
	@JoinColumn(name = "bus_id")
	private Bus bus;
	
	private BigDecimal backSeatFare;
	
	private BigDecimal middleSeatFare;
	
	private BigDecimal frontSeatFare;
	
	
	// from Airplane Entity
    private int totalSeat;
    
    private int backSeats;
	
    private int middleSeats;
    
    private int frontSeats;
	

}
