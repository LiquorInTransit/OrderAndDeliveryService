package com.gazorpazorp.model;

public class Customer {
	private Long id;
	private String stripeId;
	
	private String firstName;
	private String lastName;
	
//	private double latitude;
//	private double longitude;
	
	private Location location;
	
	public Customer() {}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



//	public double getLatitude() {
//		return latitude;
//	}
//
//	public void setLatitude(double latitude) {
//		this.latitude = latitude;
//	}
//
//	public double getLongitude() {
//		return longitude;
//	}
//
//	public void setLongitude(double longitude) {
//		this.longitude = longitude;
//	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}	
	
}
