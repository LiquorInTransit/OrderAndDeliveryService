package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findByCustomerId(@Param("customerId") Long customerId);
	
	@Query("select o from Order o where o.customerId = ?1 and status != 'complete'")
	public Order findCurrentOrderForCustomer(Long customerId);
}
