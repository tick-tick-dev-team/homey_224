package com.ticktack.homey_test.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// static으로 import하기 -> import한 클래스명 매번 안써도 됨
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ticktack.homey_test.domain.Member;

class MemoryMemberRepositoryTest {
	
	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	// 모든 메서드 실행 후 호출
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	public void save() {
		Member member = new Member();
		member.setName("yesol");
		member.setPw("1111");
		
		repository.save(member);
		
		// optional에서 값 꺼내기 : optionalObj.get();
		Member result = repository.findById(member.getId()).get();
		
		// map(DB)와 결과값이 같은지 검증
		// System.out.println("result = "+ (result == member));
		// Junit : 같은 값이 될 것을 예상
		//Assertions.assertEquals(member, result);
		
		// assertj의 Assertions.*
		assertThat(member).isEqualTo(result);
	}
	
	@Test
	public void findByName() {
		Member member1 = new Member();
		member1.setName("yesol");
		member1.setPw("1111");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("jojo");
		member2.setPw("2222");
		repository.save(member2);
		
		Member result = repository.findByName(member2.getName()).get();
		assertThat(member2).isEqualTo(result);
	}
	
	// 테스트 순서는 보장안됨. 각 테스트를 서로 독립적이게 구성할것
	// 테스트 후 데이터 클리어 로직 추가
	@Test
	public void findAll() {
		Member member1 = new Member();
		member1.setName("yesol");
		member1.setPw("1111");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("jojo");
		member2.setPw("2222");
		repository.save(member2);
		
		List<Member> resultList = repository.findAll();
		assertThat(resultList.size()).isEqualTo(2);
		
	}

}
