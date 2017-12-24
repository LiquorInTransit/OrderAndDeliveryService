package com.gazorpazorp.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.AccountClient;
import com.gazorpazorp.client.AuthenticationClient;
import com.gazorpazorp.client.ProductAndStoreClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.OrderStatus;
import com.gazorpazorp.model.Product;
import com.gazorpazorp.model.User;
import com.gazorpazorp.model.dto.DriverDto;
import com.gazorpazorp.model.dto.OrderCurrentDto;
import com.gazorpazorp.model.dtoMapper.AccountMapper;
import com.gazorpazorp.model.dtoMapper.OrderMapper;
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
	ProductAndStoreClient productClient;
	@Autowired
	AuthenticationClient authenticationClient;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	AccountMapper accountMapper;
	
	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	private static final OrderStatus[] TERMINATING_ORDER_STATUSES = {OrderStatus.COMPLETED, OrderStatus.CANCELLED};
	
	public List<Order> getAllOrdersForCustomer() {
		Long customerId = accountClient.getCustomer().getId();								//add the CANCELLED status to the valid types
		return orderRepo.findByCustomerIdAndStatusInOrderByCreatedAtDesc(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES));
	}

	public Order getOrderById(Long orderId, boolean verify) throws Exception{
		//Get the order
		Order order = orderRepo.findById(orderId).orElse(null);
		if (order==null)
			throw new Exception("Order with ID " + orderId + " does not exist");
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
		aggregateOrderItems(order);
		
		return order;
	}
	
	public OrderCurrentDto getOrderCurrentDtoById(Long orderId, boolean verify) throws Exception {
		Order order = getOrderById(orderId, verify);
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
		if (delivery == null)
			return null;
		order.setTrackingURL(delivery.getTrackingURL());
		return aggregateOrderCurrent(order, delivery);
	}
	
	public OrderCurrentDto getCurrentOrder() {
		Long customerId = accountClient.getCustomer().getId();
		Order order = orderRepo.findByCustomerIdAndStatusNotIn(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		if (order==null)
			return null;
		aggregateOrderItems(order);
		Delivery delivery = deliveryService.getDeliveryByOrderId(order.getId(), false);
		if (delivery == null)
			return null;
		order.setTrackingURL(delivery.getTrackingURL());
		return aggregateOrderCurrent(order, delivery);
		//return order;
	}
	
	private OrderCurrentDto aggregateOrderCurrent(Order order, Delivery delivery) {
		DriverDto driverDto = null;
		if (delivery.getDriverId() != null) {
			Driver driver = accountClient.getDriverById(delivery.getDriverId());
			User user = authenticationClient.getUserById(driver.getUserId());
			driverDto = accountMapper.driverAndUserToDriverDto(driver, user);
		}
		OrderCurrentDto dto = OrderMapper.INSTANCE.orderAndDriverDtoToOrderCurrentDto(order, driverDto);	
		return dto;
	}
	
	@Transactional(rollbackOn= {Exception.class} )
	public boolean cancelCurrentOrder() throws Exception {
		Long accountId = accountClient.getCustomer().getId();
		Order order = orderRepo.findByCustomerIdAndStatusNotIn(accountId, Arrays.asList(TERMINATING_ORDER_STATUSES));
		if (order == null)
			return false;
		order.setStatus(OrderStatus.CANCELLED);
		orderRepo.save(order);
		if (deliveryService.cancelDeliveryByOrderId(order.getId())==null) {
			logger.error("The delivery failed to delete");
			throw new Exception("Failed to delete order. Try again later");
		}
		return true;
	}
	
	public boolean cancelOrderByOrderId (Long orderId) throws Exception {
		Order order = orderRepo.findById(orderId).orElse(null);
		if (order == null)
			throw new Exception("Order with ID " + orderId + " does not exist");
		order.setStatus(OrderStatus.CANCELLED);
		return true;
	}
	
	
	public Order createOrder (List<LineItem> items, Long quoteId, Long customerId) throws Exception {
	//	Long customerId = accountClient.getCustomer().getId();
		if (orderRepo.findByCustomerIdAndStatusNotIn(customerId, Arrays.asList(TERMINATING_ORDER_STATUSES)) != null) {
			throw new Exception ("Customer already has an active order");
		}
		Order order = new Order();
		for (int x = 0; x<items.size(); x++)
			items.get(x).setOrder(order);
		items.forEach(item -> item.setProductId(item.getProduct().getId()));
		order.setCustomerId(customerId);
		//Remove delivery info
//		order.setDeliveryLocation("SOME RANDOM LOCATION");
//		order.setStoreLocation("SOME RANDOM LOCATION");
		//Remove Delivery Info
		order.setItems(new HashSet<LineItem>(items));
		order.setTotal(items.stream().mapToDouble(li -> li.getProduct().getPrice()*(li.getQty()*1.0)).sum());
		order.setStatus(OrderStatus.ACTIVE);
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
		order.setStatus(OrderStatus.COMPLETED);
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
	protected void aggregateOrderItems(Order order) {
		Set<Product> products = productClient.getProductsById(order.getItems().stream().map(item -> item.getProductId().toString()).collect(Collectors.joining(","))).getBody();
		order.getItems().forEach(item -> item.setProduct(products.stream().filter(prd -> item.getProductId().equals(prd.getId())).findFirst().orElse(null)));
		order.getItems().forEach(item -> {if (item!=null && item.getProduct() != null)item.getProduct().Incorporate();});
	}
}
