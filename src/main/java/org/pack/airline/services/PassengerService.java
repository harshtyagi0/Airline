package org.pack.airline.services;

import java.util.List;
import org.pack.airline.model.Passenger;
import org.springframework.data.domain.Page;

public interface PassengerService {
	
	public abstract Page<Passenger> getAllPassengersPaged(int pageNum);
	public abstract List<Passenger> getAllPassenger();
	public abstract Passenger getPassengerById(Long passengerId);
    public abstract Passenger savePassenger(Passenger passenger);
    public abstract void deletePassengerById(Long passengerId);

}
