package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.domain.item.Item;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;

	/**
	 * id 값이 없으면 save, 있으면 merge
	 * @param item
	 * @return
	 */
	public Long save(Item item) {
		if (item.getId() == null) {
			em.persist(item);
		} else {
			em.merge(item);
		}
		return item.getId();
	}

	public Item find(Long id) {
		return em.find(Item.class, id);
	}

	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}
}
