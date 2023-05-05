package com.example.jpa.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.domain.Order;
import com.example.jpa.dto.OrderSearchCriteria;
import com.example.jpa.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	public ResponseEntity search(@ModelAttribute OrderSearchCriteria criteria) {

		List<Order> orders = orderService.search(criteria);
		
		return ResponseEntity.ok(orders);
	}
}
