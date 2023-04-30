package com.example.jpa.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.domain.Member;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository repository;

	@Test
	@Transactional // @Transactional 안붙이면 entitymanager에서 트랜잭션이 없어서 에러남.
	public void save() throws Exception {
		// given
		Member member = new Member();
		member.setName("박수현");

		// when
		Long id = repository.save(member);
		Member findMember = repository.find(id);

		// then
		Assertions.assertEquals(id, findMember.getId());
		Assertions.assertEquals(member.getName(), findMember.getName());
		Assertions.assertEquals(member, findMember); // true.
		//  ==> 같은 트랜잭션 안에서 저장을 하면 같은 영속성 컨텍스트 안에 있기 때문에, 식별자가 같으면 같은 엔티티.
		// 1차 캐시에 있기 때문에 select 쿼리도 안날아감.

	}

	@Test
	@Transactional
	public void find() throws Exception {
		// given

		// when

		// then
	}
}
