package com.example.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.jpa.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	// @PersistenceContext // spring data jpa 가 @Autowired로도 영속성 컨텍스트를 잡을 수 있게 해줌.
	private final EntityManager em;

	public Long save(Member member) {
		em.persist(member); // 영속성 컨텍스트에 등록. 트랜잭션 commit 시 insert 쿼리 날아감.
		return member.getId(); // 저장 후 엔티티를 통째로 리턴하고 엔티티 수정하면 커맨드가 날아갈 수 있기 때문에, ID만 넘김.
	}

	public Member find(Long id) {
		return em.find(Member.class, id);
	}

	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name=:name", Member.class)
			.setParameter("name", name)
			.getResultList();
	}
}
