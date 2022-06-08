package org.pack.airline.services.serviceImp;

import java.util.List;

import org.pack.airline.model.Airport;
import org.pack.airline.repository.AirportRepository;
import org.pack.airline.services.AirportServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AirportServicesImp implements AirportServices {

	private AirportRepository airportRepo;

	@Autowired
	AirportServicesImp(AirportRepository airportRepo) {
		this.airportRepo = airportRepo;
	}

	@Override
	public List<Airport> getAllAirports() {
		return airportRepo.findAll();
	}

	@Override
	public Airport getAirportById(Integer airportId) {
		return airportRepo.findById(airportId).orElse(null);
	}

	@Override
	public Airport saveAirport(Airport airport) {
		return airportRepo.save(airport);
	}

	@Override
	public Page<Airport> getAllAirportsPaged(int pageNum) {
		return airportRepo.findAll(PageRequest.of(pageNum, 5, Sort.by("airportName")));
	}

	@Override
	public void deleteAirport(Integer airpportId) {
		airportRepo.deleteById(airpportId);

	}

}
