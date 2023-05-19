package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.jpa.domain.Order;
import com.example.jpa.dto.OrderSearchCriteria;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public Long save(Order order) {
		em.persist(order);

		return order.getId();
	}

	public Order find(Long id) {
		return em.find(Order.class, id);
	}

	/**
	 * String concatenation으로 짠 검색 쿼리. 나중에 QueryDSL로 바꿔야 한다.
	 * @param criteria
	 * @return
	 */
	public List<Order> search(OrderSearchCriteria criteria) {

		String jpql = "select o from Order o join o.member m";
		boolean isFirstCondition = true;

		//주문 상태 검색
		if (criteria.getOrderStatus() != null) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " o.status = :status";
		}

		//회원 이름 검색
		if (StringUtils.hasText(criteria.getMemberName())) {
			if (isFirstCondition) {
				jpql += " where";
				isFirstCondition = false;
			} else {
				jpql += " and";
			}
			jpql += " m.name like :name";
		}

		TypedQuery<Order> query = em.createQuery(jpql, Order.class)
			.setMaxResults(1000);

		if (criteria.getOrderStatus() != null) {
			query = query.setParameter("status", criteria.getOrderStatus());
		}
		if (StringUtils.hasText(criteria.getMemberName())) {
			query = query.setParameter("name", criteria.getMemberName());
		}

		return query.getResultList();
	}

	/**
	 * fetch join 사용을 위한 조회 메소드
	 * @return
	 */
	public List<Order> findAllWithMemberAndDelivery() {
		return em.createQuery(
			"select o from Order o" +
				" join fetch o.member m" +
				" join fetch o.delivery d", Order.class
		).getResultList();
	}
}
