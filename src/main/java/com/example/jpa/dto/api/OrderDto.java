package com.example.jpa.dto.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jpa.domain.Address;
import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderStatus;

import lombok.Data;

/**
 * 주문조회 V2에서 사용하는 DTO
 */
@Data
public class OrderDto {

	private Long orderId;
	private String memberName;
	private LocalDateTime orderDate;
	private OrderStatus orderStatus;
	private Address address;
	private List<OrderItemDto> orderItems;

	public OrderDto(Order order) {
		orderId = order.getId();
		memberName = order.getMember().getName();
		orderDate = order.getOrderDate();
		orderStatus = order.getOrderStatus();
		address = order.getDelivery().getAddress();
		orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
	}
}
