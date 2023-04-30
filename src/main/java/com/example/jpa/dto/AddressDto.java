package com.example.jpa.dto;

import com.example.jpa.domain.Address;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressDto {
	private String city;
	private String street;
	private String zipcode;

	/**
	 * Address 엔티티로 변환
	 * @return
	 */
	public Address toAddress() {
		return new Address(city, street, zipcode);
	}
}
