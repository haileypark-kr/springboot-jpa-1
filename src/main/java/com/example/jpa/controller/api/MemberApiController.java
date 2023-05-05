package com.example.jpa.controller.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.domain.Member;
import com.example.jpa.dto.api.CreateMemberRequest;
import com.example.jpa.dto.api.CreateMemberResponse;
import com.example.jpa.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

	private final MemberService memberService;

	/**
	 * (v1) 엔티티를 API 파라미터로 절대 받지 말고 웹에 노출하지도 마라.
	 * @param member
	 * @return
	 */
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		Long memberId = memberService.join(member);

		return new CreateMemberResponse(memberId);
	}

	/**
	 * (v2) API 파라미터용 DTO 생성.
	 * @param request
	 * @return
	 */
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		Long memberId = memberService.join(request.toMember());

		return new CreateMemberResponse(memberId);
	}

}
