package com.ticktack.homey_test.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ticktack.homey_test.domain.Member;
import com.ticktack.homey_test.service.MemberService;

@Controller
public class MemberController {
	
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
		
		// aop 사용 후 프록시 service 확인
		//System.out.println("memberService = " + memberService.getClass());
	}
	
	// 회원가입
	@GetMapping("/members/new")
	public String createMember() {
		
		return "members/createMemberForm";
	}
	
	@PostMapping("/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		member.setPw(form.getPw());
		
		memberService.join(member);
		
		return "redirect:/";
	}
	
	// 회원 목록 조회
	@GetMapping("/members")
	public String list(Model model) {
		
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		
		return "members/memberList";
	}


}
