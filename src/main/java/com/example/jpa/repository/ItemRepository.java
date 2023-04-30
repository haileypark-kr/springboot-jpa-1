package com.example.jpa.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.domain.item.Item;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;

	public Long save(Item item) {
		em.persist(item);
		return item.getId();
	}

	public Item find(Long id) {
		return em.find(Item.class, id);
	}
}
