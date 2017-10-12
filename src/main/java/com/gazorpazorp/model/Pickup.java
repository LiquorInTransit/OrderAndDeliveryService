package com.gazorpazorp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Pickup {
	@Embedded
	private Store store;
	//@Column(name="pickup_eta")
	private Integer pickupETA;
	
	public Pickup() {}
	public Pickup(Store store) {
		this.store = store;
	}	
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	public Integer getPickupETA() {
		return pickupETA;
	}
	public void setPickupETA(Integer pickupETA) {
		this.pickupETA = pickupETA;
	}
	
	
}
