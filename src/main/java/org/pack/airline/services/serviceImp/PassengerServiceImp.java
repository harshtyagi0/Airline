package org.pack.airline.services.serviceImp;

import java.util.List;

import org.pack.airline.model.Passenger;
import org.pack.airline.repository.PassengerRepository;
import org.pack.airline.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImp implements PassengerService {

	@Autowired
	PassengerRepository passengerRepo;

	public Passenger getPassengerById(Long passengerId) {

		return passengerRepo.findById(passengerId).orElse(null);

	}

	@Override
	public Passenger savePassenger(Passenger passenger) {
		// TODO Auto-generated method stub
		return passengerRepo.save(passenger);
	}

	public void deletePassengerById(Long passengerId) {
		passengerRepo.deleteById(passengerId);
	}

	@Override
	public Page<Passenger> getAllPassengersPaged(int pageNum) {
		// TODO Auto-generated method stub
		return passengerRepo.findAll(PageRequest.of(pageNum,5, Sort.by("lastName")));
	}

	@Override
	public List<Passenger> getAllPassenger() {
		// TODO Auto-generated method stub
		return passengerRepo.findAll();
	}

}
