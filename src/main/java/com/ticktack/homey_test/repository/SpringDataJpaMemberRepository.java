package com.ticktack.homey_test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticktack.homey_test.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{
	// JpaRepository를 상속하면 interface를 이용해 구현체를 자동으로 생성함
	
	// 메소드 명명 규칙에 맞게 작성 시  알아서 쿼리 작성
	@Override
	Optional<Member> findByName(String name);
}
