package org.pack.airline.services.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.pack.airline.model.Aircraft;
import org.pack.airline.repository.AircraftRepository;
import org.pack.airline.services.AircraftServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AircraftServicesImp implements AircraftServices {

	private AircraftRepository aircraftRepo;

	@Autowired
	AircraftServicesImp(AircraftRepository aircraftRepo) {
		this.aircraftRepo = aircraftRepo;
	}

	@Override
	public List<Aircraft> getAllAircrafts() {
		return aircraftRepo.findAll();
	}

	@Override
	public Aircraft getAircraftById(Long aircraftId) {
		return aircraftRepo.findById(aircraftId).orElse(null);
	}

	@Override
	public Aircraft saveAircraft(Aircraft aircraft) {
		if (aircraft == null)
			throw new IllegalArgumentException();
		return aircraftRepo.save(aircraft);
	}

	@Override
	public void deleteAircraftById(Long aircraftId) {
		aircraftRepo.deleteById(aircraftId);
	}

	@Override
	public Page<Aircraft> getAllAircraftsPaged(int pageNum) {
		return aircraftRepo.findAll(PageRequest.of(pageNum, 5, Sort.by("model")));
	}

}
