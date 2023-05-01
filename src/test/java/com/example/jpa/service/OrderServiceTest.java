package com.example.jpa.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderStatus;
import com.example.jpa.domain.item.Book;
import com.example.jpa.domain.item.Item;
import com.example.jpa.dto.AddressDto;
import com.example.jpa.dto.MemberJoinDto;
import com.example.jpa.exception.NotEnoughStockException;
import com.example.jpa.repository.OrderRepository;

@SpringBootTest
class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private MemberService memberService;

	@Autowired
	EntityManager em; //단순히 persist만 할 용도로 사용

	@Test
	@Transactional
	public void 상품주문() throws Exception {
		// given

		// - 멤버 생성
		Long memberId = createMember();

		// - 상품 생성
		Item book = createBook("시골 JPA", 10000, 10);
		em.persist(book);

		// - 주문 개수
		int orderCount = 2;

		// when
		Long orderId = orderService.order(memberId, book.getId(), orderCount);

		// then
		Order order = orderRepository.find(orderId);
		Assertions.assertEquals(OrderStatus.ORDER, order.getOrderStatus(), "상품 주문 시 상태는 ORDER");
		Assertions.assertEquals(1, order.getOrderItems().size(), "주문한 상품 아이템 수가 정확해야 한다.");
		Assertions.assertEquals(book.getPrice() * orderCount, order.getTotalPrice(), "주문 가격은 (가격*수량)이다.");
		Assertions.assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");

	}

	@Test
	@Transactional
	public void 주문취소() throws Exception {
		// given
		// - 멤버 생성
		Long memberId = createMember();

		// - 상품 생성
		Item book = createBook("시골 JPA", 10000, 10);
		em.persist(book);

		// - 주문 생성
		Long orderId = orderService.order(memberId, book.getId(), 1);

		// when
		orderService.cancel(orderId);

		// then
		Order order = orderRepository.find(orderId);
		Assertions.assertEquals(OrderStatus.CANCEL, order.getOrderStatus(), "주문 취소 시 OrderStatus는 CANCEL 상태이다.");
		Assertions.assertEquals(10, book.getStockQuantity(), "주문이 취소된 상품은 재고가 원복되어야 한다.");
	}

	@Test
	@Transactional
	public void 상품주문_재고수량초과() throws Exception {
		// given
		Long memberId = createMember();

		// - 상품 생성
		Item book = createBook("시골 JPA", 10000, 10);
		em.persist(book);

		// - 주문 개수
		int orderCount = 11;

		// when then
		Assertions.assertThrows(NotEnoughStockException.class,
			() -> orderService.order(memberId, book.getId(), orderCount));
	}

	private Long createMember() {
		// - 멤버 생성
		MemberJoinDto memberJoinDto = MemberJoinDto.builder()
			.name("박수현")
			.address(AddressDto.builder().city("수원시").street("동탄원천로").zipcode("9999").build())
			.build();
		Long memberId = memberService.join(memberJoinDto.toMember());

		return memberId;
	}

	private Item createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);

		return book;
	}
}
