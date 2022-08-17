package com.ticktack.homey_test.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.ticktack.homey_test.domain.Member;

public class JpaMemberRepository implements MemberRepository{
	
	private final EntityManager em;
	
	public JpaMemberRepository(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Member save(Member member) {
		em.persist(member); // insert 쿼리 작성+id를 member에 setId까지
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id); // 클래스 타입, 식별자로 조회 시 
		return Optional.ofNullable(member);
	}

	@Override
	public Optional<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList().stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		// eclipse inline variable (refactor) : alt + shift + i
		// jpql이라는 쿼리 언어 사용 : 객체 대상 쿼리
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}

}
