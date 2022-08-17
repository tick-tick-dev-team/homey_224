package com.ticktack.homey_test;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticktack.homey_test.aop.TimeTraceAop;
import com.ticktack.homey_test.repository.JdbcMemberRepository;
import com.ticktack.homey_test.repository.JdbcTemplateMemberRepository;
import com.ticktack.homey_test.repository.JpaMemberRepository;
import com.ticktack.homey_test.repository.MemberRepository;
import com.ticktack.homey_test.repository.MemoryMemberRepository;
import com.ticktack.homey_test.service.MemberService;

@Configuration
public class SpringConfig {
	
//	private DataSource dataSource;
//
//	@Autowired
//	public SpringConfig (DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
	
//	private EntityManager em;
//
//	@Autowired
//	public SpringConfig(EntityManager em) {
//		super();
//		this.em = em;
//	}
	
	private MemberRepository memberRepository;
	
	@Autowired
	public SpringConfig(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}

	@Bean
	public MemberService memberService() {
//		return new MemberService(memberRepository());
		return new MemberService(memberRepository); // jpa로 자동 injection된 repo 전달
	}
	
//	@Bean
//	public MemberRepository memberRepository() {
//		//return new MemoryMemberRepository();
////		return new JdbcMemberRepository(dataSource);
////		return new JdbcTemplateMemberRepository(dataSource);
////		return new JpaMemberRepository(em);
//	}
	
	

	
	
	
	
}
