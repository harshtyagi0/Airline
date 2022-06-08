package org.pack.airline.services;

import java.util.List;

import org.springframework.data.domain.Page;

import org.pack.airline.model.Airport;

public interface AirportServices {

	public abstract Page<Airport> getAllAirportsPaged(int pageNum);

	public abstract List<Airport> getAllAirports();

	public abstract Airport getAirportById(Integer airportId);

	public abstract Airport saveAirport(Airport airport);

	public abstract void deleteAirport(Integer airpportId);

}
