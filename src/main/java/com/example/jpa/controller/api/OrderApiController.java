package com.example.jpa.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderItem;
import com.example.jpa.dto.OrderSearchCriteria;
import com.example.jpa.dto.api.OrderDto;
import com.example.jpa.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

/**
 * XToMany에서의 성능 최적화
 * Order 조회
 * Order -> OrderItem 연결: OneToMany
 * OrderItem -> Item 연결: ManyToOne
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderRepository orderRepository;

	/**
	 * (v1) 주문 조회 API.
	 * - Order를 직접 다 조회한다.
	 * 문제점
	 * - LAZY 로딩 초기화하기 위해 필요한 연관관계들 초기화하는 코드가 다 필요하다.
	 * - 엔티티를 그대로 노출. Hibernate5 필요
	 */
	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1() {
		List<Order> orders = orderRepository.search(new OrderSearchCriteria());

		//  LAZY 로딩이여서 get 하기 전까지는 안가져오니까, 일부러 다 가져오게 해서 프록시 초기화를 한다.
		for (Order order : orders) {
			order.getMember().getName();
			order.getDelivery().getAddress();
			List<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				orderItem.getItem().getName();
			}
		}

		return orders;
	}

	/**
	 * (v2) 주문 조회 API.
	 * - Order를 조회하여 OrderDto에 넣는다.
	 * 문제점
	 * - 쿼리가 너무 많이 나간다. 총 (order 1 + ( delivery 1 + member 1 + orderItem 1 + item 2 ) * 2)회가 나감.
	 */
	@GetMapping("/api/v2/orders")
	public List<OrderDto> orderV2() {
		List<Order> orders = orderRepository.search(new OrderSearchCriteria());
		return orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
	}

	/**
	 * (v3) 주문 조회 API.
	 * - fetch join을 사용해서 쿼리 성능 최적화를 해보자.
	 * 문제점
	 * -
	 */
	@GetMapping("/api/v3/orders")
	public List<OrderDto> orderV3() {
		List<Order> orders = orderRepository.findAllWithItems();
		return orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
	}

}
