package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gazorpazorp.service.DeliveryService;

@Controller
@RequestMapping("/internal/conclude")
public class InternalConclusionController {
	
	@Autowired
	DeliveryService deliveryService;

	@PostMapping("/{deliveryId}")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity completeDelivery(@PathVariable("deliveryId") Long deliveryId) throws Exception {
		return Optional.ofNullable(deliveryService.completeDelivery(deliveryId))
				.map(d -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Failed to complete delivery"));
	}
	@DeleteMapping("/{deliveryId}")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity cancelDriverDelivery (@PathVariable("deliveryId") Long deliveryId) throws Exception {
		return Optional.ofNullable(deliveryService.cancelDeliveryById(deliveryId))
				.map(d -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Failed to cancel delivery"));
	}
}
