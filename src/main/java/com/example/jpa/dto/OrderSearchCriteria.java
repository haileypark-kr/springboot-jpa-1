package com.example.jpa.dto;

import com.example.jpa.domain.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchCriteria {
	private String memberName;
	private OrderStatus orderStatus;
}
