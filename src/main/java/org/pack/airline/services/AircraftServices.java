package org.pack.airline.services;

import java.util.List;

import org.springframework.data.domain.Page;

import org.pack.airline.model.Aircraft;

public interface AircraftServices {

	public abstract Page<Aircraft> getAllAircraftsPaged(int pageNum);

	public abstract List<Aircraft> getAllAircrafts();

	public abstract Aircraft getAircraftById(Long aircraftId);

	public abstract Aircraft saveAircraft(Aircraft aircraft);

	public abstract void deleteAircraftById(Long aircraftId);

}
