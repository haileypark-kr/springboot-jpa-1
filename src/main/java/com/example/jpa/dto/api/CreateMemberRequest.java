package com.example.jpa.dto.api;

import javax.validation.constraints.NotEmpty;

import com.example.jpa.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberRequest {

	@NotEmpty
	private String name;

	private Address address;

	@Data
	@AllArgsConstructor
	public static class Address {
		private String city;
		private String street;
		private String zipcode;

		public com.example.jpa.domain.Address toAddress() {
			return new com.example.jpa.domain.Address(city, street, zipcode);
		}
	}

	// === 컨버터 메소드 ===

	public Member toMember() {
		return new Member(name, address.toAddress());
	}
}
