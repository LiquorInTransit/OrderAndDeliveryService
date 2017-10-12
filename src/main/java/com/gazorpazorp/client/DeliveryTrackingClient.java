package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.client.config.TokenRequestClientConfiguration;

@FeignClient(name="delivery-tracker", configuration = TokenRequestClientConfiguration.class)
public interface DeliveryTrackingClient {

	@PostMapping("/api/tracking/new")
	public String createNewEvent(@RequestParam("deliveryId") Long deliveryId);
}
