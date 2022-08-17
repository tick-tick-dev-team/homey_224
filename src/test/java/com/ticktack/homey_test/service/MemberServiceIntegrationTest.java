package com.ticktack.homey_test.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.ticktack.homey_test.domain.Member;
import com.ticktack.homey_test.repository.MemberRepository;
import com.ticktack.homey_test.repository.MemoryMemberRepository;

@SpringBootTest
@Transactional // 종료하면서 rollback되어 테이블에 커밋되지 않음 -> 여러번 테스트 가능
class MemberServiceIntegrationTest {
	
	// test는 제일 끝단이므로 쉬운 방법 사용 (@Autowired)
	@Autowired MemberService service;
	@Autowired MemberRepository memberRepo; // 구현체 대신 인터페이스
	
	// afterEach로 memory 지워줄 필요 없음

	@Test
//	@Commit
	void 회원가입() {		
		// given : 주어지는 데이터
		Member member = new Member();
		member.setName("spring1");
		
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
		member1.setName("spring1");
		
		Member member2 = new Member();
		member2.setName("spring1");
		
		// when
		service.join(member1);
		
		// then
		IllegalStateException e = assertThrows(IllegalStateException.class, ()-> service.join(member2) );
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}

}
;