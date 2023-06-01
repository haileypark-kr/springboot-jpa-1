package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.dto.api.OrderItemQueryDto;
import com.example.jpa.dto.api.OrderQueryDto;

import lombok.RequiredArgsConstructor;

/**
 * 엔티티가 아닌, 특정 화면에 fit 한 쿼리/응답 처리하기 위한 레포지토리.
 */
@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

	private final EntityManager em;

	public List<OrderQueryDto> findOrderQueryDtos() {
		// Order 정보를 먼저 조회하고, Order.id로 OrderItems를 따로 조회.
		List<OrderQueryDto> orders = findOrders();
		orders.forEach(o -> {
			List<OrderItemQueryDto> items = findOrderItems(o.getOrderId());
			o.setOrderItems(items);
		});

		return orders;
	}

	private List<OrderQueryDto> findOrders() {
		return em.createQuery(
				// OrderItems 컬렉션을 넣을 수가 없어서 일단 여기까지만 가능.
				"select new com.example.jpa.dto.api.OrderQueryDto(o.id, m.name, o.orderDate, o.orderStatus, d.address)"
					+ " from Order o"
					+ " join o.member m"
					+ " join o.delivery d", OrderQueryDto.class)
			.getResultList();
	}

	private List<OrderItemQueryDto> findOrderItems(Long orderId) {

		// OrderItems를 조회하는 쿼리를 별도로 짜야 함.
		return em.createQuery(
				"select new com.example.jpa.dto.api.OrderItemQueryDto(i.name, oi.orderPrice, oi.count)"
					+ " from OrderItem oi"
					+ " join oi.item i"
					+ " where oi.order.id = :orderId", OrderItemQueryDto.class)
			.setParameter("orderId", orderId)
			.getResultList();
	}
}
