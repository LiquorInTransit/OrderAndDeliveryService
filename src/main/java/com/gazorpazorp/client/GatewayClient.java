package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Driver;

@FeignClient(name="gateway-service")
public interface GatewayClient {
	
	@GetMapping(value="/api/me", consumes = "application/json")
	Customer getCustomer();
	
	@GetMapping(value="/api/drivers/me")
	Driver getDriver();
}

