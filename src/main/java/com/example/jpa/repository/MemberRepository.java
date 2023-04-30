package com.example.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.jpa.domain.Member;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;

	public Long save(Member member) {
		em.persist(member); // 저장 후 엔티티를 통째로 리턴하고, 엔티티 수정하면 커맨드가 날아갈 수 있기 때문에, ID만 넘김.
		return member.getId();
	}

	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
