package com.stalmakof.diploma.controller;

import com.stalmakof.diploma.models.Booking;
import com.stalmakof.diploma.models.Flight;
import com.stalmakof.diploma.repository.BookingRepository;
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
public class BookingController {

    private BookingRepository bookingRepository;


    private FlightRepository flightRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    @RequestMapping({"/oups"})
    public String oupsHandler(){

        return "error";
    }

    @PostMapping(value = "/api/saveBooking")
    public Booking saveBooking(@RequestBody String data) {
        JSONObject bookingData = this.parseData(data);
//        log.debug("Logging from saveBk meth");

        Booking booking = new Booking();
        booking.setCustomer_name(bookingData.get("customer_name").toString());
        booking.setAddress(bookingData.get("address").toString());
        booking.setContact_no(bookingData.get("contact_no").toString());
        booking.setDeparture_date(bookingData.get("departure_date").toString());
        booking.setBooking_date(bookingData.get("booking_date").toString());
        booking.setEmail(bookingData.get("email").toString());
        booking.setFlight_id(Integer.parseInt(bookingData.get("flight_id").toString()));
        booking.getFlights().add(getFlight(Long.valueOf(booking.getFlight_id())));
//        log.debug("Logging from saveBk meth");

        return this.saveBooking(booking);
    }


    @PostMapping(value = "/api/getBookings")
    public List<Booking> getBookings() {
        return this.getAllBookings();
    }

    @PostMapping(value = "/api/editBooking")
    public Booking editBooking(@RequestBody String data) {
        try{
            JSONObject bookingData = this.parseData(data);
            Booking booking = this.getBooking(Long.parseLong(bookingData.get("booking_id").toString()));
            booking.setCustomer_name(bookingData.get("customer_name").toString());
            booking.setAddress(bookingData.get("address").toString());
            booking.setContact_no(bookingData.get("contact_no").toString());
            booking.setDeparture_date(bookingData.get("departure_date").toString());
            booking.setBooking_date(bookingData.get("booking_date").toString());
            booking.setEmail(bookingData.get("email").toString());
            booking.getFlights().add(getFlight(Long.valueOf(booking.getFlight_id())));
//            log.debug("Logging from editBk meth");
            return this.saveBooking(booking);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/api/removeBooking")
    public boolean removeBooking(@RequestBody String data) {
        JSONObject removeData = this.parseData(data);

        Booking booking = new Booking();
        booking.setId(Long.parseLong(removeData.get("booking_id").toString()));
//        log.debug("Logging from removeBk meth");
        return this.removeBooking(booking);
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

    public Booking getBooking(Long id){
        return bookingRepository.findOne(id);
    }




    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);

    }


    public boolean removeBooking(Booking booking) {
        try {
            bookingRepository.delete(booking);
            return true;
        } catch (TransactionException e) {
            return false;
        }
    }


    public List<Booking> getAllBookings() {
        Iterable<Booking> bookings = bookingRepository.findAll();
        List<Booking> data = new ArrayList<Booking>();
        for (Booking booking : bookings) {
            data.add(booking);
        }
//        log.debug("Logging from allrepoBk meth");
        return data;
    }

    public Flight getFlight(Long id){
        return flightRepository.findOne(id);
    }



}
