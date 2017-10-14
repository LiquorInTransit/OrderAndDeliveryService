package com.gazorpazorp.client;

import java.util.UUID;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.client.config.TokenRequestClientConfiguration;
import com.gazorpazorp.model.TrackingEvent;

@FeignClient(name="delivery-tracker", configuration = TokenRequestClientConfiguration.class)
public interface DeliveryTrackingClient {

	@PostMapping("/api/tracking/new")
	public String createNewEvent(@RequestParam("deliveryId") Long deliveryId);
	
	@Async
	@PostMapping("/internal/tracking/{id}")
	public void createTrackingEvent(@PathVariable("id") String trackingId, TrackingEvent trackingEvent);
}
