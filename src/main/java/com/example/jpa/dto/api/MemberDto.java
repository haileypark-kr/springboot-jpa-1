package com.example.jpa.dto.api;

import com.example.jpa.domain.Address;
import com.example.jpa.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
	private String name;
	private AddressDto address;

	public static MemberDto from(Member m) {
		return new MemberDto(m.getName(), AddressDto.from(m.getAddress()));
	}

	@Data
	@AllArgsConstructor
	public static class AddressDto {
		private String city;
		private String street;
		private String zipcode;

		public static AddressDto from(Address address) {
			return new AddressDto(address.getCity(), address.getStreet(), address.getZipcode());
		}
	}
}
