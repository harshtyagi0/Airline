package org.pack.airline.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.pack.airline.model.Airport;
import org.pack.airline.model.Flight;

public interface FlightService {

	public abstract Page<Flight> getAllFlightsPaged(int pageNum);

	public abstract List<Flight> getAllFlights();

	public abstract Flight getFlightById(long flightId);

	public abstract Flight saveFlight(Flight flight);

	public abstract void deleteFlightById(long flightId);

	public abstract List<Flight> getAllFlightsByAirportAndDepartureTime(Airport depAirport, Airport destAirport,
			LocalDate depDate);
}
