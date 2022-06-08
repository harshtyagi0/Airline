package org.pack.airline.repository;

import java.time.LocalDate;

import java.util.List;

import org.pack.airline.model.Airport;
import org.pack.airline.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
	List<Flight> findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(Airport depAirport,
			Airport destAirport, LocalDate depDate);

}