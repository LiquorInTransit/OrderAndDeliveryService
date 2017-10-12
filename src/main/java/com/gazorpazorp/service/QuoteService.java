package com.gazorpazorp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.GatewayClient;
import com.gazorpazorp.client.StoreClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Dropoff;
import com.gazorpazorp.model.Pickup;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.Store;
import com.gazorpazorp.repository.QuoteRepository;

@Service
public class QuoteService {
	Logger logger = LoggerFactory.getLogger(QuoteService.class);
	
	private final double FEE = 5.00;
	private double feeMultiplier = 1.00;

	@Autowired
	QuoteRepository quoteRepo;
	
	@Autowired
	GatewayClient acctClient;
	@Autowired
	StoreClient storeClient;
	
	public Quote createQuote () throws Exception {
		Quote quote = new Quote();
		Customer customer = acctClient.getCustomer();
		logger.error("These are the coordinates obtained from the customer: " + customer.getLocation().getLatitude()+", "+customer.getLocation().getLongitude());
		Store store = storeClient.getClosestStoreToCoords(customer.getLocation().getLatitude(), customer.getLocation().getLongitude());
		
		Dropoff dropoff = new Dropoff();
		dropoff.setLocation(customer.getLocation());
		dropoff.setCustomerId(customer.getId());
		dropoff.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
		
		
		quote.setCustomerId(customer.getId());
		quote.setPickup(new Pickup(store));
		quote.setDropoff(dropoff);
//		quote.setDeliveryLat(customer.getLatitude());
//		quote.setDeliveryLong(customer.getLongitude());
//		quote.setPickupLat(store.getLatitude());
//		quote.setPickupLong(store.getLongitude());
		//TODO: contact order service to find all new orders in last hour. if its higher than 20, change feeMultiplier
		quote.setFee(FEE*feeMultiplier);
		
		//TODO: Contact google maps to get drive times from pickup to delivery
		int dropoffETA = 11;
		quote.setDropoffETA(dropoffETA);
		
		
		return quoteRepo.save(quote);
	}
	
	public Quote getQuoteById(Long id) {
		return quoteRepo.findById(id).orElse(null);
	}
}
