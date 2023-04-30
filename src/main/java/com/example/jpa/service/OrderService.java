package com.example.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.domain.Delivery;
import com.example.jpa.domain.Member;
import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderItem;
import com.example.jpa.domain.item.Item;
import com.example.jpa.dto.OrderSearchCriteria;
import com.example.jpa.repository.ItemRepository;
import com.example.jpa.repository.MemberRepository;
import com.example.jpa.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	/**
	 * 주문 생성
	 * @param memberId
	 * @param itemId
	 * @param count
	 * @return
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		// 엔티티 조회
		Member member = memberRepository.find(memberId);
		Item item = itemRepository.find(itemId);

		// 배송 정보 생성 (간단하게, 회원의 주소 정보와 동일하게 넣음)
		// - 별도 저장 안함. Order 저장 시 Cascade
		Delivery delivery = new Delivery(member.getAddress());

		// 주문 상품(OrderItem) 생성
		// - 별도 저장 안함. Order 저장 시 Cascade
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		// 주문(Order) 생성 (Order 생성 시 Delivery, OrderItem CASCADE ALL)
		Order order = Order.createOrder(member, delivery, orderItem);
		orderRepository.save(order);

		return order.getId();
	}

	/**
	 * 주문 취소
	 * @param orderId
	 */
	@Transactional
	public void cancel(Long orderId) {
		// 주문 엔티티 조회
		Order order = orderRepository.find(orderId);

		// 엔티티에 주문 취소 위임
		order.cancel();

		// repository에 업데이트 쿼리 안날린다!!!
	}

	// 검색
	public List<Order> search(OrderSearchCriteria orderSearchCriteria) {
		return orderRepository.search(orderSearchCriteria);
	}
}
