package com.example.jpa.dto;

import com.example.jpa.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberJoinDto {
	private String name;
	private AddressDto address;

	/**
	 * Member 엔티티로 변환
	 * @return
	 */
	public Member toMember() {
		Member member = new Member();
		member.setName(name);
		member.setAddress(address.toAddress());

		return member;
	}
}
