package com.example.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.jpa.domain.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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
}
