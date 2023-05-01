package com.example.jpa.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.domain.Category;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
	private final EntityManager em;

	public Long save(Category item) {
		em.persist(item);
		return item.getId();
	}

	public Category find(Long id) {
		return em.find(Category.class, id);
	}

}
