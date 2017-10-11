package com.gazorpazorp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.LineItem;
import com.gazorpazorp.service.OrderService;

@RestController
@RequestMapping("/internal/orders")
public class InternalOrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping("/{orderId}/items")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity getOrderById (@PathVariable Long orderId, Principal principal) throws Exception {
		return Optional.ofNullable(orderService.getOrderById(orderId, false))
				.map(o -> new ResponseEntity<List<LineItem>>(new ArrayList<LineItem>(o.getItems()), HttpStatus.OK))
				.orElseThrow(() -> new Exception("Order does not exist"));
	}
}
