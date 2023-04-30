package com.example.jpa.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 주의: immutable!!
// setter 오픈 안함. 값 변경 필요 시 새로 생성.
@Getter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	private String city;
	private String street;
	private String zipcode;
}
