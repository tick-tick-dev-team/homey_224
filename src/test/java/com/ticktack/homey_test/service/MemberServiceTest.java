package com.ticktack.homey_test.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ticktack.homey_test.domain.Member;
import com.ticktack.homey_test.repository.MemoryMemberRepository;

class MemberServiceTest {
	
//	MemberService service = new MemberService();
//	MemoryMemberRepository memberRepo = new MemoryMemberRepository();
	// service 클래스의 repo와 다른 객체라는 문제
	
	MemberService service;
	MemoryMemberRepository memberRepo;
	
	@BeforeEach
	public void beforeEach() {
		// 각 테스트마다 repo 먼저 생성 -> service에 repo 넣어주기 : DI (dependency injection, 의존관계주입)
		memberRepo = new MemoryMemberRepository();
		service = new MemberService(memberRepo);
	}
	
	
	@AfterEach
	public void afterEach() {
		memberRepo.clearStore();
	}
	

	@Test
	void 회원가입() {		
		// given : 주어지는 데이터
		Member member = new Member();
		member.setName("mem1");
		member.setPw("1111");
		
		// when : 검증조건
		Long savedId = service.join(member);
		
		// then : 원하는 결과
		// 가입한 정보가 repo에 있는지 찾기
		Member findMember = service.findOne(savedId).get();
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	public void 중복회원예외() {
		// given
		Member member1 = new Member();
		member1.setName("mem1");
		member1.setPw("1111");
		
		Member member2 = new Member();
		member2.setName("mem1");
		member2.setPw("1111");
		
		// when
		service.join(member1);
		
		// then
		
//		try {
//			service.join(member2); // 중복회원 에러 발생
//			fail();
//		} catch(IllegalStateException e) {
//			assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.111");
//		}

		IllegalStateException e = assertThrows(IllegalStateException.class, ()-> service.join(member2) );
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}

}
;