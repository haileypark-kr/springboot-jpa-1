package com.example.jpa.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.domain.Category;
import com.example.jpa.domain.item.Item;
import com.example.jpa.repository.CategoryRepository;
import com.example.jpa.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

	private final ItemRepository itemRepository;
	private final CategoryRepository categoryRepository;

	@Transactional
	public Long saveItem(Long categoryId, Item item) {
		// 엔티티 조회
		Category category = categoryRepository.find(categoryId);

		// 상품 생성
		item.setCategories(Collections.singletonList(category));

		// 상품 저장
		itemRepository.save(item);

		return item.getId();
	}

	/**
	 * 영속성 컨텍스트에 없는 아이템을 DB에서 조회하여 영속 엔티티로 만든 후 업데이트한다.
	 * public setter를 사용한 업데이트 로직이므로 deprecation.
	 * @param itemId
	 * @param param
	 */
	@Deprecated
	@Transactional
	public void updateItem_deprecated(Long itemId, Item param) {
		Item item = itemRepository.find(itemId); // 영속 상태로 만듦
		item.setPrice(param.getPrice()); // 이렇게 세팅하지 마라.
		item.setName(param.getName()); // 이렇게 세팅하지 마라.
		item.setStockQuantity(param.getStockQuantity()); // 이렇게 세팅하지 마라.

		// 영속 상태에 있으므로 save 호출 불필요.
	}

	/**
	 * 업데이트 시에는 엔티티 내부에서 업데이트하고 외부에서 하지 마라.
	 * ==> 그래야 값이 어디서 바뀌는지 추적 가능.
	 * @param itemId
	 * @param param
	 */
	@Transactional
	public void updateItem(Long itemId, Item param) {
		Item item = itemRepository.find(itemId);
		item.change(param.getPrice(), param.getName(), param.getStockQuantity()); // 엔티티 내부에서 업데이트.
	}

	public List<Item> findAll() {
		return itemRepository.findAll();
	}
}
