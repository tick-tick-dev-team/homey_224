package com.ticktack.homey_test.repository;

import java.util.List;
import java.util.Optional;

import com.ticktack.homey_test.domain.Member;

public interface MemberRepository {
	
	// 회원 저장 메소드
	Member save(Member member);
	
	// id, name이 null 인 경우 대비해 optional로 감싸서 반환하는 조회 메소드
	Optional<Member> findById(Long id);
	Optional<Member> findByName(String name);
	
	// 모든 멤버 조회 메소드
	List<Member> findAll();

}
