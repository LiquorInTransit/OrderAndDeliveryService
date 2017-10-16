package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.model.Store;

@FeignClient(name="product-and-store-service")
public interface StoreClient {
	
	@GetMapping("/api/stores/locate")
	public Store getClosestStoreToCoords(@RequestParam("lat")double latitude, @RequestParam("long")double longitude);
}
