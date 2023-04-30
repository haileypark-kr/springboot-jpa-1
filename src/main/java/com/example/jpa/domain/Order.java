package com.example.jpa.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // Setter는 웬만하면 사용하지 말고, 생성자로만 값을 만들고 연관관계 편의 메소드로 하자.
@Entity
@Table(name = "ORDERS") // order로 테이블 생성 X
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_MEMBER")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DELIVERY_ID") // OneToOne에서 FK는 조회를 더 자주 하는 곳에 넣기.
	private Delivery delivery;

	private LocalDateTime orderDate;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus orderStatus; // [ORDER, CANCEL]

	// === 연관 관계 편의 메소드 ===
	public void changeMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		getOrderItems().add(orderItem);
		orderItem.setOrder(this);
	}

	public void changeDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	// === 생성 메소드 ===

	/**
	 * 주문 생성.
	 * @param member
	 * @param delivery
	 * @param orderItems
	 * @return
	 */
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem item : orderItems) {
			order.addOrderItem(item);
		}

		order.setOrderStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}

	// === 비즈니스 로직 메소드 ===

	/**
	 * 주문 취소. delivery.deliveryStatus==COMPLETE인 경우 IllegalStateException 발생
	 */
	public void cancel() {
		if (delivery.getDeliveryStatus() == DeliveryStatus.COMPLETE) {
			throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
		}

		this.setOrderStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}

	// === 조회 메소드 ===

	/**
	 * 전체 가격 조회
	 * @return
	 */
	public int getTotalPrice() {
		return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
}
