package com.gazorpazorp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.model.Order;
import com.gazorpazorp.model.dto.OrderMinimalList;
import com.gazorpazorp.model.dtoMapper.OrderMapper;
import com.gazorpazorp.repository.OrderRepository;
import com.gazorpazorp.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@PostMapping
	@PreAuthorize("#oauth2.hasScope('orders')")
	public ResponseEntity<Order> createOrder (@RequestBody List<LineItem> items, @RequestParam("quote") Long quoteId) throws Exception {
		return Optional.ofNullable(orderService.createOrder(items, quoteId))
				.map(o -> new ResponseEntity<Order>(o, HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not create order!"));
	}
	
	@GetMapping("/history")
	@PreAuthorize("#oauth2.hasScope('orders')")
	public ResponseEntity<OrderMinimalList> getAll() throws Exception{
		return Optional.ofNullable(orderService.getAllOrdersForCustomer())
				.map(o -> new ResponseEntity<OrderMinimalList>
						(new OrderMinimalList(o.stream()
						.map(order -> OrderMapper.INSTANCE.orderToOrderMinimalDto(order))
						.collect(Collectors.toList())), HttpStatus.OK)
					)
				.orElseThrow(() -> new Exception("Account does not exist"));
	}
	
	@GetMapping("/{orderId}")
	@PreAuthorize("#oauth2.hasScope('orders')")
	public ResponseEntity getOrderById (@PathVariable Long orderId) throws Exception {
		return Optional.ofNullable(orderService.getOrderById(orderId, true))
				.map(o -> new ResponseEntity<Order>(o, HttpStatus.OK))
				.orElseThrow(() -> new Exception("Customer not authorized to view this order"));//TODO: Change this back to 'Account does not exist' when the other TODO in the service is updated
	}
	
	@GetMapping("/current")
	@PreAuthorize("#oauth2.hasScope('orders')")
	public ResponseEntity<Order> getCurrentOrder () throws Exception {
		return Optional.ofNullable(orderService.getCurrentOrder())
				.map(o -> new ResponseEntity<Order>(o, HttpStatus.OK))
				.orElseThrow(() -> new Exception("No Current Order"));
	}
	
	@DeleteMapping("/current")
	@PreAuthorize("#oauth2.hasScope('orders')")
	public ResponseEntity deleteCurrentOrder () throws Exception {
		orderService.deleteCurrentOrder();
		return new ResponseEntity(null, HttpStatus.OK);
	}

	
	
}
