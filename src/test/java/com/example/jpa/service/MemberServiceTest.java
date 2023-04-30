package com.example.jpa.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.dto.AddressDto;
import com.example.jpa.dto.MemberJoinDto;
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
		MemberJoinDto memberJoinDto = MemberJoinDto.builder()
			.name("박수현")
			.address(AddressDto.builder().city("수원시").street("동탄원천로").zipcode("9999").build())
			.build();

		// when
		Long id = memberService.join(memberJoinDto.toMember());

		// then
		Assertions.assertEquals(memberJoinDto.getName(), memberRepository.find(id).getName());
	}

	@Test
	@Transactional
	public void 중복회원가입() throws Exception {
		// given
		MemberJoinDto memberJoinDto = MemberJoinDto.builder()
			.name("박수현")
			.address(AddressDto.builder().city("수원시").street("동탄원천로").zipcode("9999").build())
			.build();
		MemberJoinDto memberJoinDto2 = MemberJoinDto.builder()
			.name("박수현")
			.address(AddressDto.builder().city("수원시").street("동탄원천로").zipcode("1111").build())
			.build();

		// when
		memberService.join(memberJoinDto.toMember());

		// then
		Assertions.assertThrows(IllegalStateException.class, () -> {
			memberService.join(memberJoinDto2.toMember());
		});
	}
}
