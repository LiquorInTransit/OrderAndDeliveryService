package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Dropoff {

	private Long customerId;
	private String customerName;
	@Embedded
	private Location location;
	//@Column(name="dropoff_eta")
	private Integer dropoffETA;
	
	public Dropoff() {}
	
	
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Integer getDropoffETA() {
		return dropoffETA;
	}
	public void setDropoffETA(Integer dropoffETA) {
		this.dropoffETA = dropoffETA;
	}
	
	
	
	
	
}
