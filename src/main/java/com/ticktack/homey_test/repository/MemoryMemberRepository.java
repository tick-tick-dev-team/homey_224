package com.ticktack.homey_test.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ticktack.homey_test.domain.Member;

public class MemoryMemberRepository implements MemberRepository{
	
	// 회원정보가 저장될 map
	private static Map<Long, Member> store = new HashMap<>();
	private static Long sequence = 0L;

	@Override
	public Member save(Member member) {
		// sequence값 올려서 id로 저장
		member.setId(++sequence);
		// map에 저장
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		
		// id 조회결과 없어서 null 반환되는 경우를 대비해 optional로 감싸서 반환
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Optional<Member> findByName(String name) {
		
		// java filter : 조건에 맞는 객체 찾으면 반환, 없으면 optional로 감싸서 반환됨
		return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
	}

	@Override
	public List<Member> findAll() {
		// map을 arraylist로 바꿔서 반환
		return new ArrayList<>(store.values());
	}
	
	public void clearStore() {
		store.clear();
	}

}
