package com.gazorpazorp.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.AccountClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.repository.LineItemRepository;
import com.gazorpazorp.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	LineItemRepository lineItemRepo;
	@Autowired
	AccountClient accountClient;
	
	@Autowired
	DeliveryService deliveryService;
	
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	public List<Order> getAllOrdersForCustomer() {
		Long customerId = accountClient.getCustomer().getId();
		return orderRepo.findByCustomerId(customerId).stream().filter(o -> "complete".equals(o.getStatus())).collect(Collectors.toList());
	}

	public Order getOrderById(Long orderId, boolean verify) {
		//Get the order
		Order order = orderRepo.findById(orderId).get();
		
		//validate that the accountId of the order belongs to the user
		if (verify) {
			try {
				validateCustomerId(order.getCustomerId());
			} catch (Exception e) {
				//TODO: Make this throw an exception so that feign can say that you're not authorized to look at these orders
				logger.error("FAILED VALIDATION");
				return null;
			}
		}
//		if (order==null)
//			return null;
//		order.setTrackingURL(deliveryClient.getDeliveryByOrderId(order.getId()).getBody().getTrackingURL());
		return order;
	}
	
	public Order getCurrentOrder() {
		Long customerId = accountClient.getCustomer().getId();
		Order order = orderRepo.findCurrentOrderForCustomer(customerId);
		if (order==null)
			return null;
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
		if (delivery == null)
			return null;
		order.setTrackingURL(delivery.getTrackingURL());
		return order;
	}
	
	@Transactional(rollbackOn= {Exception.class} )
	public boolean deleteCurrentOrder() throws Exception {
		Long accountId = accountClient.getCustomer().getId();
		Order order = orderRepo.findCurrentOrderForCustomer(accountId);
		if (order == null)
			return false;
		orderRepo.delete(order);
		if (deliveryService.deleteDeliveryByOrderId(order.getId())==null) {
			logger.error("The delivery failed to delete");
			throw new Exception("Failed to delete order. Try again later");
		}
		return true;
	}
	
	
	public Order createOrder (List<LineItem> items, Long quoteId, Long customerId) throws Exception {
	//	Long customerId = accountClient.getCustomer().getId();
		if (orderRepo.findCurrentOrderForCustomer(customerId) != null) {
			throw new Exception ("Customer already has an active order");
		}
		Order order = new Order();
		for (int x = 0; x<items.size(); x++)
			items.get(x).setOrder(order);
		order.setCustomerId(customerId);
		//Remove delivery info
//		order.setDeliveryLocation("SOME RANDOM LOCATION");
//		order.setStoreLocation("SOME RANDOM LOCATION");
		//Remove Delivery Info
		order.setItems(new HashSet<LineItem>(items));
		order.setTotal(items.stream().mapToDouble(li -> li.getPrice()*(li.getQty()*1.0)).sum());
		order.setStatus("picking_items");
//		order.setCreatedAt(new Date());
		order = orderRepo.saveAndFlush(order);
	//Create delivery from quote
	//If something failed, then delete the order we just created, and return null;
		String trackingURL = "";
		try {
			trackingURL = deliveryService.createDelivery(quoteId, order.getId());	
		} catch (Exception e) {
			e.printStackTrace();
			//The delivery creation failed
			orderRepo.deleteById(order.getId());
			return null;
		}
		
		order.setTrackingURL(trackingURL);
		return order;
	}
	
	public Boolean completeOrder(Long orderId) throws Exception {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new Exception("Order of ID" + orderId + " does not exist"));
		order.setStatus("complete");
		orderRepo.save(order);
		return true;
	}
	


	private boolean validateCustomerId(Long customerId) throws Exception {
		Customer customer = accountClient.getCustomer();
		
		if (customer != null && customer.getId() != customerId) {
			throw new Exception ("Account number not valid");
		}
		return true;
	}
}
