package com.busreservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busreservation.dao.JourneyBookingDao;
import com.busreservation.entity.Journey;
import com.busreservation.entity.JourneyBooking;
import com.busreservation.entity.User;

@Service
public class JourneyBookingServiceImpl implements JourneyBookingService {
	
	@Autowired
	private JourneyBookingDao journeyBookingDao;

	@Override
	public JourneyBooking add(JourneyBooking journeyBooking) {
		// TODO Auto-generated method stub
		return journeyBookingDao.save(journeyBooking);
	}

	@Override
	public JourneyBooking getById(int id) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findById(id).get();
	}

	@Override
	public List<JourneyBooking> getAll() {
		// TODO Auto-generated method stub
		return journeyBookingDao.findAll();
	}

	@Override
	public List<JourneyBooking> getByPassenger(User user) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByPassenger(user);
	}

	@Override
	public List<JourneyBooking> getByJourney(Journey journey) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByJourney(journey);
	}

	@Override
	public List<JourneyBooking> getByJourneyAndStatus(Journey journey, String status) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByJourneyAndStatus(journey, status);
	}

	@Override
	public List<JourneyBooking> getByJourneyAndStatusNotIn(Journey journey, List<String> status) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByJourneyAndStatusNotIn(journey, status);
	}

	@Override
	public List<JourneyBooking> getByJourneyAndStatusAndJourneyClass(Journey journey, String status, String journeyClass) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByJourneyAndStatusAndJourneyClass(journey, status, journeyClass);
	}

	@Override
	public List<JourneyBooking> getByJourneyAndStatusNotInAndJourneyClass(Journey journey, List<String> status,
			String journeyClass) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByJourneyAndStatusNotInAndJourneyClass(journey, status, journeyClass);
	}

	@Override
	public List<JourneyBooking> getByJourneyAndJourneyClass(Journey journey, String journeyClass) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByJourneyAndJourneyClass(journey, journeyClass);
	}

	@Override
	public List<JourneyBooking> getByIdAndStatus(int id, String status) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByIdAndStatus(id, status);
	}

	@Override
	public List<JourneyBooking> getByStatusIn(List<String> statuses) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByStatusIn(statuses);
	}

	@Override
	public List<JourneyBooking> getByBookingId(String bookingId) {
		// TODO Auto-generated method stub
		return journeyBookingDao.findByBookingId(bookingId);
	}

}
