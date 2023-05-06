package com.example.jpa.controller.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.domain.Member;
import com.example.jpa.dto.api.CreateMemberRequest;
import com.example.jpa.dto.api.CreateMemberResponse;
import com.example.jpa.dto.api.UpdateMemberRequest;
import com.example.jpa.dto.api.UpdateMemberResponse;
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

	/**
	 * (V2) Put으로 수정할 경우, 똑같은 요청을 여러 번 날려도 문제가 발생하지 않음.
	 * @param memberId
	 * @param request Update와 Create는 API 스펙이 일반적으로 다르기 때문에, 별도의 DTO 써라.
	 * @return
	 */
	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long memberId,
		@RequestBody @Valid UpdateMemberRequest request) {

		// 업데이트 시에는 필요한 필드만 넘기는게 베스트. 수정할 필드가 너무 많으면 DTO 넘기기.
		memberService.update(memberId, request.getName());

		// 커맨드와 쿼리 분리를 위해, 다시 멤버 조회.
		Member findMember = memberService.findOne(memberId);

		return new UpdateMemberResponse(findMember.getId(), findMember.getName());
	}
}
