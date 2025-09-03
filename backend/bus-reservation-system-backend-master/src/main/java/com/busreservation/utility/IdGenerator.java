package com.busreservation.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdGenerator {
	
	public static String generateBookingId() {
		UUID uuid = UUID.randomUUID();
        String uuidHex = uuid.toString().replace("-", ""); // Remove hyphens
        String uuid16Digits = uuidHex.substring(0, 16); // Take the first 16 characters
        
        return uuid16Digits;
    }
	
	public static String generateJourneyNumber() {
		UUID uuid = UUID.randomUUID();
        String uuidHex = uuid.toString().replace("-", ""); // Remove hyphens
        String uuid5Digits = uuidHex.substring(0, 5); // Take the first 16 characters
        
        return uuid5Digits;
    }
	
	public static List<String> generateSeatNumbers(int backSeats, int middleSeats, int frontSeats) {
        List<String> seatNumbers = new ArrayList<>();

        for (int i = 1; i <= backSeats; i++) {
            seatNumbers.add("B-" + i);
        }

        for (int i = 1; i <= middleSeats; i++) {
            seatNumbers.add("M-" + i);
        }

        for (int i = 1; i <= frontSeats; i++) {
            seatNumbers.add("F-" + i);
        }

        return seatNumbers;
    }

}
