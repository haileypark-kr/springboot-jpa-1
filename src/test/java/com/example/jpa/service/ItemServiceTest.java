package com.example.jpa.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.domain.Category;
import com.example.jpa.domain.item.Book;
import com.example.jpa.domain.item.Item;

@SpringBootTest
class ItemServiceTest {

	@Autowired
	ItemService itemService;

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	public void 아이템등록() throws Exception {
		// given

		// - 카테고리 생성
		Long categoryId = createCategory();

		// - 아이템 생성
		String name = "수현북";
		String isbn = "123";

		Book book = (Book)createBook(name, 10000, 100, isbn, "박수");

		// when
		itemService.saveItem(categoryId, book);

		// then
		Assertions.assertEquals(name, book.getName(), "상품의 이름이 정확하다.");
		Assertions.assertEquals(isbn, book.getIsbn(), "책의 isbn이 정확하다");
		Assertions.assertEquals(categoryId, book.getCategories().get(0).getId(), "카테고리가 정확하다.");
	}

	@Test
	@Transactional
	public void 아이템수정() throws Exception {
		// given
		// - 카테고리 생성
		Long categoryId = createCategory();

		// - 아이템 생성
		String name = "수현북";
		String isbn = "123";

		Book book = (Book)createBook(name, 10000, 100, isbn, "박수");

		// - 아이템 저장
		Long itemId = itemService.saveItem(categoryId, book);

		// - 아이템 생성
		name = "선두북";
		isbn = "456";
		book.setName(name);
		book.setIsbn(isbn);

		// when
		itemService.saveItem(categoryId, book);

		// then
		Assertions.assertEquals(name, book.getName(), "변경된 상품명이 정확하다.");
		Assertions.assertEquals(isbn, book.getIsbn(), "변경된 ISBN 값이 정확하다.");
		Assertions.assertEquals(10000, book.getPrice(), "변경되지 않은, 가격이 기존과 동일하다.");
		Assertions.assertEquals(categoryId, book.getCategories().get(0).getId(), "변경되지 않은, 카테고리가 기존과 동일하다.");
	}

	private Long createCategory() {
		Category parentCategory = new Category("부모 카테고리", null);
		Category childCategory = new Category("자식 카테고리", parentCategory);
		parentCategory.addChildCategory(childCategory);
		em.persist(parentCategory);
		em.persist(childCategory);

		return childCategory.getId();
	}

	private Item createBook(String name, int price, int stockQuantity, String isbn, String author) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		book.setIsbn(isbn);
		book.setAuthor(author);
		return book;
	}
}
