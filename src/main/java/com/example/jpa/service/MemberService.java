package com.example.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.jpa.domain.Member;
import com.example.jpa.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository repository;

	/**
	 * 회원 가입
	 * @param member name 필드는 unique
	 * @return
	 */
	@Transactional(readOnly = false)
	public Long join(Member member) {

		validateDuplicateMember(member);

		repository.save(member);

		return member.getId(); // 영속성컨텍스트에 올라간 엔티티는 반드시 ID값이 있음.
	}

	private void validateDuplicateMember(Member member) {
		// duplicate 시 exception
		if (!CollectionUtils.isEmpty(repository.findByName(member.getName()))) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	// 회원 목록 전체 조회
	public List<Member> findAll() {
		return repository.findAll();
	}
}
