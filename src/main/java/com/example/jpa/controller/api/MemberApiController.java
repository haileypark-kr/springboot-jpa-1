package com.example.jpa.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.domain.Member;
import com.example.jpa.dto.api.CreateMemberRequest;
import com.example.jpa.dto.api.CreateMemberResponse;
import com.example.jpa.dto.api.MemberDto;
import com.example.jpa.dto.api.Result;
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

	/**
	 * (V1) 응답으로 엔티티를 넘기면 엔티티에 연관된 모든 정보가 다 들어간다. (orders..)
	 *  - 엔티티 변경되면 API 스펙이 변경된다.
	 *  - 컬렉션을 반환하면 향후 API 스펙 변경하기가 더 어렵다(count라도 넣어달라고 하면?). data 등으로 한 번 더 감싸야 함.
	 * @return
	 */
	@GetMapping("/api/v1/members")
	public List<Member> membersV1() {
		return memberService.findAll();
	}

	/**
	 * (V2) 응답으로 Result<T> 객체 만들어서 응답. 컬렉션을 바로 반환하지 않고 data에 넣어서 반환한다.
	 *  - API 스펙 변경 원할 경우(count 필드처럼) 바로 추가 가능
	 * @return
	 */
	@GetMapping("/api/v2/members")
	public Result<List<MemberDto>> membersV2() {
		List<Member> members = memberService.findAll();
		List<MemberDto> collect = members.stream()
			.map(MemberDto::from)
			.collect(Collectors.toList());

		return new Result<>(collect.size(), collect);
	}
}
