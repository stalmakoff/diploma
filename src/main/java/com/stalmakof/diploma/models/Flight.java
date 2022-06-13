package com.stalmakof.diploma.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flight")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@ManyToMany
//	@JoinTable(name="flights_bookings", joinColumns = @JoinColumn(name = "flight_id"),
//			inverseJoinColumns = @JoinColumn(name = "booking_id"))
//	private Set<Booking> bookings = new HashSet<Booking>();

	@Column
	private String code;

	@Column
	private String category;

	@Column
	private String airline;

	@Column
	private String source;

	@Column
	private String destination;

	@Column
	private Integer av_seats;

	@Column
	private Boolean status;


//	public Set<Booking> getBookings() {
//		return bookings;
//	}
//
//	public void setBookings(Set<Booking> bookings) {
//		this.bookings = bookings;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getAv_seats() {
		return av_seats;
	}

	public void setAv_seats(Integer av_seats) {
		this.av_seats = av_seats;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
