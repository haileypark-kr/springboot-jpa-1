package com.example.jpa.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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

		try {
			repository.save(member);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}

		return member.getId(); // 영속성컨텍스트에 올라간 엔티티는 반드시 ID값이 있음.
	}

	private void validateDuplicateMember(Member member) {
		// duplicate 시 exception
		if (!CollectionUtils.isEmpty(repository.findByName(member.getName()))) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * ID로 회원 조회
	 * @param id
	 * @return
	 */
	public Member findOne(Long id) {
		return repository.find(id);
	}

	/**
	 * 회원 목록 전체 조회
	 * @return
	 */
	public List<Member> findAll() {
		return repository.findAll();
	}

	/**
	 * 멤버 업데이트. 변경 감지 사용
	 * @param memberId
	 * @param name
	 */
	@Transactional
	public void update(Long memberId, String name) {
		Member member = repository.find(memberId); // 변경 감지를 위해 ID로 엔티티 먼저 조회.
		member.updateName(name);

		// 멤버를 반환하지 않음. 밖에서 ID로 멤버 다시 조회.
		// 1. 트랜잭션 밖으로 엔티티가 나가버려서, 영속 상태가 끊긴 멤버가 반환된다.
		// 2. 커맨드와 쿼리를 분리해야 한다. 멤버를 여기서 반환해버리면 update만 하는게 아니라 조회도 해버리게 됨.
	}
}
