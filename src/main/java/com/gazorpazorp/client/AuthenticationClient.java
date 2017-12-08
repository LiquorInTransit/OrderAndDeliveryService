package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gazorpazorp.client.config.TokenRequestClientConfiguration;
import com.gazorpazorp.model.User;

@FeignClient(name="uaa-service", configuration = TokenRequestClientConfiguration.class)
public interface AuthenticationClient {

	@GetMapping("/api/users/{id}")
	public User getUserById(@PathVariable("id") Long id);
}
