package com.example.jpa.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.dto.api.CreateMemberRequest;
import com.example.jpa.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@Transactional
	public void 회원가입() throws Exception {
		// given
		CreateMemberRequest memberJoinDto = new CreateMemberRequest("박수현",
			new CreateMemberRequest.Address("수원시", "동탄원천로", "9999"));

		// when
		Long id = memberService.join(memberJoinDto.toMember());

		// then
		Assertions.assertEquals(memberJoinDto.getName(), memberRepository.find(id).getName());
	}

	@Test
	@Transactional
	public void 중복회원가입() throws Exception {
		// given
		CreateMemberRequest memberJoinDto = new CreateMemberRequest("박수현",
			new CreateMemberRequest.Address("수원시", "동탄원천로", "9999"));
		CreateMemberRequest memberJoinDto2 = new CreateMemberRequest("박수현",
			new CreateMemberRequest.Address("수원시", "동탄원천로", "1000"));

		// when
		memberService.join(memberJoinDto.toMember());

		// then
		Assertions.assertThrows(IllegalStateException.class, () -> {
			memberService.join(memberJoinDto2.toMember());
		});
	}
}
