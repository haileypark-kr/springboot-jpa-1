package com.example.jpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@NotEmpty
	@Column(unique = true)
	private String name;

	@Embedded
	private Address address;

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>(); // 컬렉션은 조회 후에 함부로 수정하거나 (생성자 등에서) 초기화하지 마라. hibernate에서 이상동작함.

	public Member(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	public void updateName(String name) {
		setName(name);
	}
}
