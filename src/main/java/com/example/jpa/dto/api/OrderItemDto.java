package com.example.jpa.dto.api;

import com.example.jpa.domain.OrderItem;

import lombok.Data;

/**
 * 주문조회V2에 사용되는 OrderItem용 DTO
 */
@Data
public class OrderItemDto {

	private Long id;
	private String itemName;
	private int orderPrice;
	private int count;

	public OrderItemDto(OrderItem orderItem) {
		id = orderItem.getId();
		itemName = orderItem.getItem().getName();
		orderPrice = orderItem.getOrderPrice();
		count = orderItem.getCount();
	}
}
