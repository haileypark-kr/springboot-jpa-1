package com.example.jpa.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.domain.Order;
import com.example.jpa.dto.OrderSearchCriteria;
import com.example.jpa.dto.api.SimpleOrderDto;
import com.example.jpa.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

/**
 * 심플 버전. XToOne에서의 성능 최적화
 * Order 조회
 * Order -> Member 연결: ManyToOne
 * Order -> Delivery 연결: OneToOne
 * (Order -> OrderItem 연결: OneToMany => 컬렉션이기 때문에 복잡해서 이 컨트롤러에서는 안함)
 */
@RestController
@RequiredArgsConstructor
public class SimpleOrderApiController {
	private final OrderRepository orderRepository;

	/**
	 * (v1) 간단한 주문 조회 API.
	 * - Order의 목록을 조회해야 하는데, 이 때 Member, Delivery 등을 다 연결해서 가져오게 되면 무슨 문제가 발생하는지 보여줌
	 * 문제점
	 * - Order 엔티티 그대로 반환: 연관관계에 있는 모든 엔티티를 무한루프로 가져오게 됨
	 * - 잭슨이 LAZY로딩 때 쓰이는 hibernate 프록시 객체 변환 못함 (Hibernate5 모듈 써야 함)
	 * - List 반환
	 */
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> orders = orderRepository.search(new OrderSearchCriteria());
		return orders;
	}

	/**
	 * (v2) 간단한 주문 조회 API.
	 * - 엔티티가 아닌 DTO를 반환한다.
	 * 문제점
	 * - LAZY 로딩으로 인한 N+1 문제
	 * 	테이블 3개 (Order, Member, Delivery)를 조회해야 하는데, 쿼리가 Order 1번, Member, Delivery 2번씩 나감 (order가 2개라)
	 * - 최악의 경우 총 order 1번, member N번, Delivery N번 찌르게 됨.
	 * - List 반환
	 * @return
	 */
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		List<Order> orders = orderRepository.search(new OrderSearchCriteria());
		return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
	}

	/**
	 * (v3) 간단한 주문 조회 API.
	 * - fetch join 사용
	 * 문제점
	 * - List 반환
	 * @return
	 */
	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithMemberAndDelivery();
		return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
	}

}
