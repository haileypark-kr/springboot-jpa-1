package com.example.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery {

	@Id
	@GeneratedValue
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(value = EnumType.STRING)
	private DeliveryStatus deliveryStatus;
}
