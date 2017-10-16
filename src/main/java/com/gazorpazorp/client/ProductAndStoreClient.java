package com.gazorpazorp.client;

import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gazorpazorp.client.config.TokenRequestClientConfiguration;
import com.gazorpazorp.model.Product;
import com.gazorpazorp.model.Store;

@FeignClient(name="product-and-store-service", configuration = TokenRequestClientConfiguration.class)
public interface ProductAndStoreClient {
	
	@GetMapping("/api/stores/locate")
	public Store getClosestStoreToCoords(@RequestParam("lat")double latitude, @RequestParam("long")double longitude);
	
	@GetMapping(value="/internal/products", consumes="application/json")
	ResponseEntity<Set<Product>> getProductsById(@RequestParam("productIds")String ids);
}
