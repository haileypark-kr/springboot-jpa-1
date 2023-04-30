package com.example.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.jpa.domain.item.Item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ITEM")
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ORDER")
	private Order order;

	private int orderPrice; // 주문 당시 가격

	private int count; // 주문 수량

	// === 생성 메소드 ===

	/**
	 * OrderItem 생성 메소드
	 * @param item
	 * @param orderPrice
	 * @param count item.stockQuantity에 count 만큼 재고 감소.
	 * @return
	 */
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);

		item.removeStock(count);

		return orderItem;
	}

	// === 비즈니스 로직 메소드 ===

	/**
	 * 주문 취소. item.stockQuantity에 count만큼 수량 증가.
	 */
	public void cancel() {
		item.setStockQuantity(item.getStockQuantity() + count);
	}

	// === 조회 로직 메소드 ===

	public int getTotalPrice() {
		return count * orderPrice;
	}
}
