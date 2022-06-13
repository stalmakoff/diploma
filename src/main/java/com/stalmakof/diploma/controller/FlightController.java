package com.stalmakof.diploma.controller;

import com.stalmakof.diploma.models.Flight;
import com.stalmakof.diploma.repository.FlightRepository;
import org.hibernate.TransactionException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Slf4j
@RestController
public class FlightController {


    private FlightRepository flightRepository;

    @Autowired
    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @PostMapping(value = "/api/getFlights")
    public List<Flight> getFlights() {
        return this.getAllFlights();
    }


    @PostMapping(value = "/api/saveFlight")
    public Flight saveFlight(@RequestBody String data) {
        JSONObject flightData = this.parseData(data);

        Flight flight = new Flight();
        flight.setCode(flightData.get("code").toString());
        flight.setAirline(flightData.get("airline").toString());
        flight.setCategory(flightData.get("category").toString());
        flight.setSource(flightData.get("source").toString());
        flight.setDestination(flightData.get("destination").toString());
        flight.setAv_seats(Integer.parseInt(flightData.get("seats").toString()));
        flight.setStatus(true);
//        log.debug("Logging from saveFL meth");

        return this.saveFlight(flight);
    }

    @PostMapping(value = "/api/editFlight")
    public Flight editFlight(@RequestBody String data) {
        try{
            JSONObject flightData = this.parseData(data);
            Flight flight = this.getFlight(Long.parseLong(flightData.get("flightID").toString()));
            flight.setCategory(flightData.get("new_flight_category").toString());
            flight.setCode(flightData.get("new_flight_code").toString());
            flight.setAirline(flightData.get("new_flight_airline").toString());
            flight.setAv_seats(Integer.parseInt(flightData.get("new_flight_seats").toString()));
            flight.setSource(flightData.get("new_flight_source").toString());
            flight.setDestination(flightData.get("new_flight_destination").toString());
//            log.debug("Logging from editFL meth");

            return this.saveFlight(flight);
        } catch (Exception e) {
            return null;
        }
    }


    @PostMapping(value = "/api/removeFlight")
    public boolean removeFlight(@RequestBody String data) {
        JSONObject removeData = this.parseData(data);

        Flight flight = new Flight();
        flight.setId(Long.parseLong(removeData.get("flight_id").toString()));
//        log.debug("Logging from rmFL meth");
        return this.removeFlight(flight);
    }

    public boolean removeFlight(Flight flight) {
        try {
//            log.debug("Logging from rem rep Fl meth");
            flightRepository.delete(flight);
            return true;
        } catch (TransactionException e) {
            return false;
        }
    }

    @RequestMapping({"/oupss"})
    public String oupsHandler(){

        return "error";
    }

    private Flight saveFlight(Flight flight){
//        log.debug("Logging from repoFl meth");
        return flightRepository.save(flight);
    }

    private JSONObject parseData(String data) {
        try {
            JSONParser parser = new JSONParser();
//            log.debug("Logging from par meth");
            return (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Flight getFlight(Long id){
        return flightRepository.findOne(id);
    }


    public List<Flight> getAllFlights() {
        Iterable<Flight> flights = flightRepository.findAll();
        List<Flight> data = new ArrayList<Flight>();
        for (Flight flight : flights) {
            data.add(flight);
        }
//        log.debug("Logging from allFLRepo meth");
        return data;
    }

}
