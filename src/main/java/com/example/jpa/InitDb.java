package com.example.jpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.domain.Address;
import com.example.jpa.domain.Delivery;
import com.example.jpa.domain.Member;
import com.example.jpa.domain.Order;
import com.example.jpa.domain.OrderItem;
import com.example.jpa.domain.item.Book;

import lombok.RequiredArgsConstructor;

/**
 * DB 초기화.
 * userA
 *   - JPA book 1
 *   - JPA book 2
 * userB
 *   - Spring book 1
 *   - Spring book 2
 */
@Component
@RequiredArgsConstructor
public class InitDb {

	private final InitService initService;

	@PostConstruct
	public void init() {
		// @PostConstruct 에는 @Transactional 이 잘 안먹혀서 별도 서비스로 분리.
		initService.dbInit1();
		initService.dbInit2();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {
		private final EntityManager em;

		public void dbInit1() {
			Member member = createMember("userA", "서울", "1가", "1111");
			em.persist(member);

			Book jpaBook1 = createBook("JPA 1", 10000, 100, "123123", "김영한");
			em.persist(jpaBook1);

			Book jpaBook2 = createBook("JPA 2", 12000, 100, "44444", "김영한");
			em.persist(jpaBook2);

			OrderItem orderItem1 = OrderItem.createOrderItem(jpaBook1, 10000, 1); // order 저장 시 cascade
			OrderItem orderItem2 = OrderItem.createOrderItem(jpaBook2, 14000, 2);

			Delivery delivery = new Delivery(member.getAddress()); // order 저장 시 cascade

			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
			em.persist(order);
		}

		public void dbInit2() {
			Member member = createMember("userB", "수원", "2가", "2222");
			em.persist(member);

			Book springBook1 = createBook("Spring 1", 20000, 200, "5555", "백기선");
			em.persist(springBook1);

			Book springBook2 = createBook("Spring 2", 21000, 200, "6666", "백기선");
			em.persist(springBook2);

			OrderItem orderItem1 = OrderItem.createOrderItem(springBook1, 21000, 1); // order 저장 시 cascade
			OrderItem orderItem2 = OrderItem.createOrderItem(springBook2, 22000, 1);

			Delivery delivery = new Delivery(member.getAddress()); // order 저장 시 cascade

			Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
			em.persist(order);
		}

		private static Member createMember(String name, String city, String street, String zipcode) {
			Member member = new Member(name, new Address(city, street, zipcode));
			return member;
		}

		private static Book createBook(String name, int price, int stockQuantity, String isbn, String author) {
			Book jpaBook1 = new Book();
			jpaBook1.setName(name);
			jpaBook1.setPrice(price);
			jpaBook1.setStockQuantity(stockQuantity);
			jpaBook1.setIsbn(isbn);
			jpaBook1.setAuthor(author);
			return jpaBook1;
		}

	}
}
