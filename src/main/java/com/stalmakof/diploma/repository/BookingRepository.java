package com.stalmakof.diploma.repository;

import org.springframework.data.repository.CrudRepository;

import com.stalmakof.diploma.models.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {

}
