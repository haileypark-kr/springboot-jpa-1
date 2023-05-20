package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.dto.api.SimpleOrderDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

	private final EntityManager em;

	public List<SimpleOrderDto> findOrderDtos() {
		return em.createQuery(
			"select new com.example.jpa.dto.api.SimpleOrderDto(o.id, o.member.name, o.orderDate, o.orderStatus, o.delivery.address)"
				+ " from Order o"
				+ " join o.member m"
				+ " join o.delivery d", SimpleOrderDto.class
		).getResultList();
	}
}
