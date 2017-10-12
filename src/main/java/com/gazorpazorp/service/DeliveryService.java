package com.gazorpazorp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.DeliveryTrackingClient;
import com.gazorpazorp.client.GatewayClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.Location;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.Quote;
import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.model.TrackingEventType;
import com.gazorpazorp.model.dto.DeliveryWithItemsDto;
import com.gazorpazorp.model.dtoMapper.DeliveryMapper;
import com.gazorpazorp.repository.DeliveryRepository;
import com.gazorpazorp.repository.QuoteRepository;

@Service
public class DeliveryService {
	Logger logger = LoggerFactory.getLogger(DeliveryService.class);

	@Autowired
	QuoteRepository quoteRepo;
	@Autowired
	DeliveryRepository deliveryRepo;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GatewayClient accountClient;
	@Autowired
	DeliveryTrackingClient deliveryTrackingClient;

	// @Autowired
	// DeliveryTrackerClient deliveryTrackerClient;

	public String createDelivery(Long quoteId, Long orderId) {
		Quote quote = quoteRepo.findById(quoteId).get();

		Delivery delivery = new Delivery();
		delivery.setPickup(quote.getPickup());
		delivery.setDropoff(quote.getDropoff());
		delivery.setQuoteId(quote.getId());
		delivery.setOrderId(orderId);
		delivery.setFee(quote.getFee());
		delivery.setStatus("");
		delivery = deliveryRepo.save(delivery);
		// TODO: REMOVE THIS AWFUL TESTING SHIT
		delivery.setTrackingURL(deliveryTrackingClient.createNewEvent(delivery.getId()));
		// delivery.setTrackingURL(deliveryTrackerClient.track(delivery.getId()))
		// if (delivery.getTrackingURL() != null)
		deliveryRepo.save(delivery);
		return delivery.getTrackingURL();
	}

	public Delivery getDeliveryByOrderId(Long orderId, boolean verifyCustomer) {
		Delivery delivery = deliveryRepo.findByOrderId(orderId);
		
		if (verifyCustomer) {
			//validate that the accountId of the order belongs to the user
			try {
				validateCustomerId(delivery.getDropoff().getCustomerId());
			} catch (Exception e) {
				// TODO: Make this throw an exception so that feign can say that you're not
				// authorized to look at these deliveries
				logger.error("FAILED VALIDATION");
				return null;
			}
		}

		return delivery;
	}
	
	public Delivery getDeliveryById(Long deliveryId, boolean verifyDriver) throws Exception {
		Delivery delivery = deliveryRepo.findById(deliveryId).orElseThrow(() -> new Exception("Delivery of that ID does not exist"));
		
		if (verifyDriver) {
			//validate that the accountId of the order belongs to the user
			try {
				validateDriverId(delivery.getDriverId());
			} catch (Exception e) {
				// TODO: Make this throw an exception so that feign can say that you're not
				// authorized to look at these deliveries
				logger.error("FAILED VALIDATION");
				return null;
			}
		}

		return delivery;
	}
	
	@Transactional
	public Boolean deleteDeliveryByOrderId(Long orderId) {
		deliveryRepo.deleteByOrderId(orderId);
		Delivery delivery = deliveryRepo.findByOrderId(orderId);
		if (delivery == null)
			return true;

		return null;
	}
	
	//Not sure if this method even works...
	public List<Delivery> getDeliveriesForCustomer() {
		return deliveryRepo.findByDropoffCustomerId(accountClient.getCustomer().getId());
	}
	
	public List<Delivery> getDriverHistory() {
		return deliveryRepo.findByDriverId(accountClient.getDriver().getId());
	}
	
	public DeliveryWithItemsDto getDriverCurrentDelivery() {
		Driver driver = accountClient.getDriver();
		Delivery delivery = deliveryRepo.findCurrentDeliveryForDriver(driver.getId());
		if (delivery == null)
			return null;
		Order order = orderService.getOrderById(delivery.getOrderId(), false);
		if (order == null)
			return null;
		return DeliveryMapper.INSTANCE.deliveryAndItemsToDeliveryWithItemsDto(delivery, new ArrayList<LineItem>(order.getItems()));
	}
	
	public Delivery findOpen() {
		Driver driver = accountClient.getDriver();
		if ( !deliveryRepo.findByDriverId(driver.getId()).stream().filter(d -> !"complete".equals(d.getStatus())).collect(Collectors.toList()).isEmpty())
			return null;
		List<Delivery> openDeliveries = deliveryRepo.findTopByDriverIdIsNullAndDriverHoldIsNullOrderByCreatedAtAsc(driver.getId());
		Delivery delivery = openDeliveries.isEmpty()?null:openDeliveries.get(0);
		if (delivery != null) {
			delivery.setDriverHold(driver.getId());
			deliveryRepo.saveAndFlush(delivery);
		}
		return delivery;
	}
	
	public DeliveryWithItemsDto assignDelivery (Long deliveryId, Location location) {
		Driver driver = accountClient.getDriver();
		Delivery delivery = deliveryRepo.findById(deliveryId).orElse(null);
		if (delivery == null)
			return null;
		if (delivery.getDriverHold() != driver.getId() || delivery.getDriverId() != null)
			return null;
		
		delivery.setDriverId(driver.getId());
		delivery = deliveryRepo.save(delivery);
		Order order = orderService.getOrderById(delivery.getOrderId(), false);
		if (order == null)
			return null;
		
		//Create the accepted event
		TrackingEvent trackingEvent = new TrackingEvent();
		trackingEvent.setDeliveryId(deliveryId);
		trackingEvent.setTrackingEventType(TrackingEventType.RECEIVED_DELIVERY);
		trackingEvent.setLocation(location);
		deliveryTrackingClient.createTrackingEvent(deliveryId, trackingEvent);
		return DeliveryMapper.INSTANCE.deliveryAndItemsToDeliveryWithItemsDto(delivery, new ArrayList<LineItem>(order.getItems()));
	}
	
	public Boolean removeHold (Long deliveryId) throws Exception {
		Delivery delivery = deliveryRepo.findById(deliveryId).orElse(null);
		if (delivery == null)
			throw new Exception("Delivery does not exist");
		if (delivery.getDriverId()!=null)
			throw new Exception("A driver is already assigned to this delivery");			
		Driver driver = accountClient.getDriver();
		if (delivery.getDriverHold() != driver.getId())
			throw new Exception("You are not authorized to remove the hold from this order");
		
		delivery.setDriverHold(null);
	//	delivery.getDriverBlacklist().add(driver.getId());
		deliveryRepo.save(delivery);
		return true;
	}
	
	
	
	
	
	
	private boolean validateCustomerId(Long customerId) throws Exception {
		Customer customer = accountClient.getCustomer();
		
		if (customer != null && customer.getId() != customerId) {
			throw new Exception ("Account number not valid");
		}
		return true;
	}
	private boolean validateDriverId(Long driverId) throws Exception {
		Driver driver = accountClient.getDriver();
		
		if (driver == null || driver.getId() != driverId) {
			throw new Exception ("Driver not valid");
		}
		return true;
	}
}
