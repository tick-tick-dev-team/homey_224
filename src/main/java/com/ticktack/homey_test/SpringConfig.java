package com.ticktack.homey_test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticktack.homey_test.repository.MemberRepository;
import com.ticktack.homey_test.repository.MemoryMemberRepository;
import com.ticktack.homey_test.service.MemberService;

@Configuration
public class SpringConfig {

	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}
	
	@Bean
	public MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
}
