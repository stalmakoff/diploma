package com.stalmakof.diploma.repository;

import org.springframework.data.repository.CrudRepository;

import com.stalmakof.diploma.models.Flight;

public interface FlightRepository extends CrudRepository<Flight, Long> {

}
