package com.example.jpa.dto.api;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UpdateMemberRequest {

	@NotEmpty
	private String name;

}
