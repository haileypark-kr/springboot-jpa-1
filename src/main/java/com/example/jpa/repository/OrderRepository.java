package com.example.jpa.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.domain.Order;
import com.example.jpa.dto.OrderSearchCriteria;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public Long save(Order order) {
		em.persist(order);

		return order.getId();
	}

	public Order find(Long id) {
		return em.find(Order.class, id);
	}

	public List<Order> search(OrderSearchCriteria orderSearchCriteria) {
		return new ArrayList<>();
	}
}
