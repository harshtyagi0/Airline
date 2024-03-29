package org.pack.airline.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.pack.airline.model.User;
import org.pack.airline.model.*;
import org.pack.airline.services.*;

@Controller
public class MainController {

	@Autowired
	AirportServices airportService;
	@Autowired
	AircraftServices aircraftService;
	@Autowired
	FlightService flightService;
	@Autowired
	PassengerService passengerService;

	@Autowired
	UserService usrService;

	@GetMapping("/")
	public String showHomePage() {
		return "index";
	}

	@GetMapping("/index")
	public String showindexPage() {
		return "index";
	}

	@GetMapping("/admin")
	public String showAdminPage() {
		return "admin";
	}

	@GetMapping("/dash")
	public String showDashPage() {
		return "Dash";
	}

	@GetMapping("/about")
	public String showaboutPage() {
		return "about";
	}
	
	

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/user/new")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// String encodedPassword = passwordEncoder.encode(user.getPassword());
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		usrService.saveUser(user);
		return "register_success";
	}

	@GetMapping("/airport/new")
	public String showAddAirportPage(Model model) {
		model.addAttribute("airport", new Airport());
		return "newAirport";
	}

	@PostMapping("/airport/new")
	public String saveAirport(@Valid @ModelAttribute("airport") Airport airport, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			model.addAttribute("airport", new Airport());
			return "newAirport";
		}
		airportService.saveAirport(airport);
		model.addAttribute("airports", airportService.getAllAirportsPaged(0));
		model.addAttribute("currentPage", 0);
		return "airports";
	}

	@GetMapping("/airport/delete")
	public String deleteAirport(@PathParam("airportId") int airportId, Model model) {
		airportService.deleteAirport(airportId);
		model.addAttribute("airports", airportService.getAllAirportsPaged(0));
		model.addAttribute("currentPage", 0);
		return "airports";
	}

	@GetMapping("/admin/airports")
	public String showAirportsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
		model.addAttribute("airports", airportService.getAllAirportsPaged(pageNo));
		model.addAttribute("currentPage", pageNo);
		return "airports";
	}

	@GetMapping("/admin/aircraft/new")
	public String showAddAircraftPage(Model model) {
		model.addAttribute("aircraft", new Aircraft());
		return "newAircraft";
	}

	@PostMapping("/aircraft/new")
	public String saveAircraft(@Valid @ModelAttribute("aircraft") Aircraft aircraft, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			model.addAttribute("aircraft", new Aircraft());
			return "newAircraft";
		}
		aircraftService.saveAircraft(aircraft);
		model.addAttribute("aircrafts", aircraftService.getAllAircraftsPaged(0));
		model.addAttribute("currentPage", 0);
		return "aircrafts";
	}

	@GetMapping("/aircraft/delete")
	public String deleteAircraft(@PathParam("aircraftId") long aircraftId, Model model) {
		aircraftService.deleteAircraftById(aircraftId);
		model.addAttribute("aircrafts", aircraftService.getAllAircraftsPaged(0));
		model.addAttribute("currentPage", 0);
		return "aircrafts";
	}

	@GetMapping("/admin/aircrafts")
	public String showAircraftsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
		model.addAttribute("aircrafts", aircraftService.getAllAircraftsPaged(pageNo));
		model.addAttribute("currentPage", pageNo);
		return "aircrafts";
	}
	
	

	@GetMapping("/flight/new")
	public String showNewFlightPage(Model model) {
		model.addAttribute("flight", new Flight());
		model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
		model.addAttribute("airports", airportService.getAllAirports());
		return "newFlight";
	}

	@PostMapping("/flight/new")
	public String saveFlight(@Valid @ModelAttribute("flight") Flight flight, BindingResult bindingResult,
			@RequestParam("departureAirport") int departureAirport,
			@RequestParam("destinationAirport") int destinationAirport, @RequestParam("aircraft") long aircraftId,
			@RequestParam("arrivalTime") String arrivalTime, @RequestParam("departureTime") String departureTime,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			model.addAttribute("flight", new Flight());
			model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
			model.addAttribute("airports", airportService.getAllAirports());
			return "newFlight";
		}
		if (departureAirport == destinationAirport) {
			model.addAttribute("sameAirportError", "Departure and destination airport can't be same");
			model.addAttribute("flight", new Flight());
			model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
			model.addAttribute("airports", airportService.getAllAirports());
			return "newFlight";
		}

		flight.setAircraft(aircraftService.getAircraftById(aircraftId));
		flight.setDepartureAirport(airportService.getAirportById(departureAirport));
		flight.setDestinationAirport(airportService.getAirportById(destinationAirport));
		flight.setDepartureTime(departureTime);
		flight.setArrivalTime(arrivalTime);
		flightService.saveFlight(flight);

		model.addAttribute("flights", flightService.getAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);
		return "flights";
	}

	@GetMapping("/flight/delete")
	public String deleteFlight(@PathParam("flightId") long flightId, Model model) {
		flightService.deleteFlightById(flightId);
		model.addAttribute("flights", flightService.getAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);
		return "flights";
	}
	
	@GetMapping("/passenger/delete")
	public String deletePassenger(@PathParam("passengerId") long passengerId, Model model) {
		passengerService.deletePassengerById(passengerId);
		model.addAttribute("passenger", passengerService.getAllPassengersPaged(0));
		model.addAttribute("currentPage", 0);
		model.addAttribute("flights", flightService.getAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);
		return "flights";
	}

	@GetMapping("/admin/flights")
	public String showFlightsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
		model.addAttribute("flights", flightService.getAllFlightsPaged(pageNo));
		model.addAttribute("currentPage", pageNo);
		return "flights";
	}

	@GetMapping("/flight/search")
	public String showSearchFlightPage(Model model) {
		model.addAttribute("airports", airportService.getAllAirports());
		model.addAttribute("flights", null);
		return "searchFlight";
	}

	@PostMapping("/flight/search")
	public String searchFlight(@RequestParam("departureAirport") int departureAirport,
			@RequestParam("destinationAirport") int destinationAirport,
			@RequestParam("departureTime") String departureTime, Model model) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate deptTime = LocalDate.parse(departureTime, dtf);
		Airport depAirport = airportService.getAirportById(departureAirport);
		Airport destAirport = airportService.getAirportById(destinationAirport);

		if (departureAirport == destinationAirport) {
			model.addAttribute("AirportError", "Departure and destination airport cant be same!");
			model.addAttribute("airports", airportService.getAllAirports());
			return "searchFlight";
		}
		List<Flight> flights = flightService.getAllFlightsByAirportAndDepartureTime(depAirport, destAirport, deptTime);
		if (flights.isEmpty()) {
			model.addAttribute("notFound", "No Record Found!");
		} else {
			model.addAttribute("flights", flights);
		}

		model.addAttribute("airports", airportService.getAllAirports());
		return "searchFlight";
	}

	@GetMapping("/flight/book")
	public String showBookFlightPage(Model model) {
		model.addAttribute("airports", airportService.getAllAirports());
		return "bookFlight";
	}

	@PostMapping("/flight/book")
	public String searchFlightToBook(@RequestParam("departureAirport") int departureAirport,
			@RequestParam("destinationAirport") int destinationAirport,
			@RequestParam("departureTime") String departureTime, Model model) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate deptTime = LocalDate.parse(departureTime, dtf);
		Airport depAirport = airportService.getAirportById(departureAirport);
		Airport destAirport = airportService.getAirportById(destinationAirport);

		if (departureAirport == destinationAirport) {
			model.addAttribute("AirportError", "Departure and destination airport cant be same!");
			model.addAttribute("airports", airportService.getAllAirports());
			return "bookFlight";
		}
		List<Flight> flights = flightService.getAllFlightsByAirportAndDepartureTime(depAirport, destAirport, deptTime);
		if (flights.isEmpty()) {
			model.addAttribute("notFound", "No Record Found!");
		} else {
			model.addAttribute("flights", flights);
		}
		model.addAttribute("airports", airportService.getAllAirports());
		return "bookFlight";
	}

	@GetMapping("/flight/book/new")
	public String showCustomerInfoPage(@RequestParam long flightId, Model model) {
		model.addAttribute("flightId", flightId);
		model.addAttribute("passenger", new Passenger());
		return "newPassenger";
	}

	@PostMapping("/flight/book/new")
	public String bookFlight(@Valid @ModelAttribute("passenger") Passenger passenger, BindingResult bindingResult,
			@RequestParam("flightId") long flightId, Model model) {
		Flight flight = flightService.getFlightById(flightId);
		Passenger passenger1 = passenger;
		passenger1.setFlight(flight);
		passengerService.savePassenger(passenger1);
		model.addAttribute("passenger", passenger1);
		return "confirmationPage";
	}



	@PostMapping("/flight/book/cancel")
	public String cancelTicket(@RequestParam("passengerId") long passengerId, Model model) {
		passengerService.deletePassengerById(passengerId);
		model.addAttribute("flights", flightService.getAllFlightsPaged(0));
		model.addAttribute("currentPage", 0);
		return "flights";
	}

	@GetMapping("passengers")
	public String showPassengerList(@RequestParam long flightId, Model model) {
		List<Passenger> passengers = flightService.getFlightById(flightId).getPassengers();
		model.addAttribute("passengers", passengers);
		model.addAttribute("flight", flightService.getFlightById(flightId));
		return "passengers";
	}
	
	

	@GetMapping("/newPassenger")
	public String addPassenger() {
		return "newPassenger";
	}
	
	@GetMapping(value = {"/userIndex","flight/book/userIndex"})
	public String userIndex() {
		return "userIndex";
	}
	
	@GetMapping("/flight/userIndex")
	public String userIndexP() {
		return "userIndex";
	}

	@GetMapping("/admin/adminlogin")
	public String logedin() {
		return "adminlogin";
	}

}
