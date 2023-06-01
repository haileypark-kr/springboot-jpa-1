package com.example.jpa.dto.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.jpa.domain.Address;
import com.example.jpa.domain.OrderStatus;

import lombok.Data;

@Data
public class OrderQueryDto {
	private Long orderId;
	private String name;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Address address;
	private List<OrderItemQueryDto> orderItems = new ArrayList<>();

	public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.address = address;
		// this.orderItems = orderItems;
	}
}
