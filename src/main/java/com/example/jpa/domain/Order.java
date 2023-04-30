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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter // Setter는 웬만하면 사용하지 말고, 생성자로만 값을 만들고 연관관계 편의 메소드로 하자.
@Entity
@Table(name = "ORDERS") // order로 테이블 생성 X
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

}
