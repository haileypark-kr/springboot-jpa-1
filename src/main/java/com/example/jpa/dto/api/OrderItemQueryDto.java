package com.example.jpa.dto.api;

import lombok.Data;

@Data
public class OrderItemQueryDto {
	private String itemName;
	private int orderPrice;
	private int count;

	public OrderItemQueryDto(String itemName, int orderPrice, int count) {
		this.itemName = itemName;
		this.orderPrice = orderPrice;
		this.count = count;
	}
}
