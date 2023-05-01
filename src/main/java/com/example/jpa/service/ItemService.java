package com.example.jpa.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jpa.domain.Category;
import com.example.jpa.domain.item.Item;
import com.example.jpa.repository.CategoryRepository;
import com.example.jpa.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	private final CategoryRepository categoryRepository;

	public Long saveItem(Long categoryId, Item item) {
		// 엔티티 조회
		Category category = categoryRepository.find(categoryId);

		// 상품 생성
		item.setCategories(Collections.singletonList(category));

		// 상품 저장
		itemRepository.save(item);

		return item.getId();
	}

	public List<Item> findAll() {
		return itemRepository.findAll();
	}
}
