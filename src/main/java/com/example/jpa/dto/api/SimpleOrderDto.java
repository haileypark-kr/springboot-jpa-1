package com.example.jpa.dto.api;

import java.time.LocalDateTime;

import com.example.jpa.domain.Address;
import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 주문 조회 V2 API를 위한 간단한 DTO
 */
@Data
@NoArgsConstructor
public class SimpleOrderDto {

	private Long orderId;
	private String memberName;
	private LocalDateTime orderTime;
	private OrderStatus orderStatus;
	private Address deliveryAddress; // Address는 엔티티가 아닌, VO. Immutable.

	public SimpleOrderDto(Long orderId, String memberName, LocalDateTime orderTime, OrderStatus orderStatus,
		Address deliveryAddress) {
		this.orderId = orderId;
		this.memberName = memberName;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * DTO에 엔티티를 넣는건 문제가 되지 않음.
	 * @param order
	 */
	public SimpleOrderDto(Order order) {
		this.orderId = order.getId();
		this.memberName = order.getMember().getName();
		this.orderTime = order.getOrderDate();
		this.orderStatus = order.getOrderStatus();
		this.deliveryAddress = order.getDelivery().getAddress();
	}
}
