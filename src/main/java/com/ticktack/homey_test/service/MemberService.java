package com.ticktack.homey_test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticktack.homey_test.domain.Member;
import com.ticktack.homey_test.repository.MemberRepository;
import com.ticktack.homey_test.repository.MemoryMemberRepository;


public class MemberService {
	
	private final MemberRepository memberRepo;
	
	// repo를 외부에서 넣어주게 변경
	
	public MemberService(MemberRepository memberRepo) {
		this.memberRepo = memberRepo;
	}

	/**
	 * 회원가입
	 */
	public Long join(Member member) {
		// 중복 이름 불가
		// result.orElseGet(other); // get() 대신 사용. null인 경우 실행할 메소드 전달
		// optional로 감싸면 optional의 여러 기능 사용 가능 (if null 대신)
		// 코드1
		/**
		Optional<Member> result = memberRepo.findByName(member.getName());

		result.ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
		*/
		
		validateDuplicateMember(member); // 중복회원 검증
	
		memberRepo.save(member);
		return member.getId();		
	}
	
	private void validateDuplicateMember(Member member) {
		//코드2
		// 하나의 기능으로 분리 가능하면 메소드화
		memberRepo.findByName(member.getName())
				.ifPresent(m -> {
						throw new IllegalStateException("이미 존재하는 회원입니다.");
				});
	};
	
	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepo.findAll();
	}
	
	
	/**
	 * 아이디로 회원 조회
	 */
	public Optional<Member> findOne (Long memberId) {
		return memberRepo.findById(memberId);
	}
	
	
	
}
