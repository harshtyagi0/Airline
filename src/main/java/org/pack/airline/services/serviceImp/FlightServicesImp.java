package org.pack.airline.services.serviceImp;

import java.time.LocalDate;
import java.util.List;

import org.pack.airline.model.Airport;
import org.pack.airline.model.Flight;
import org.pack.airline.repository.FlightRepository;
import org.pack.airline.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FlightServicesImp implements FlightService {
	
	private FlightRepository flightrepo;

	@Autowired
	public FlightServicesImp(FlightRepository flightrepo) {
		this.flightrepo = flightrepo;
	}

	@Override
	public List<Flight> getAllFlights() {
		return flightrepo.findAll();
	}

	@Override
	public Flight getFlightById(long flightId) {
		return flightrepo.findById(flightId).orElse(null);
	}

	@Override
	public void deleteFlightById(long flightId) {
		flightrepo.deleteById(flightId);
	}

	@Override
	public Flight saveFlight(Flight flight) {
		return flightrepo.save(flight);
	}

	@Override
	public List<Flight> getAllFlightsByAirportAndDepartureTime(Airport depAirport, Airport destAirport,
			LocalDate depDate) {
		return flightrepo.findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(depAirport,
				destAirport, depDate);
	}

	@Override
	public Page<Flight> getAllFlightsPaged(int pageNum) {
		return flightrepo.findAll(PageRequest.of(pageNum, 5, Sort.by("departureAirport")));
	}

}
