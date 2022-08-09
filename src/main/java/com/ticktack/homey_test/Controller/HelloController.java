package com.ticktack.homey_test.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@GetMapping("hello")
	public String hello(Model model) {
		model.addAttribute("data", "예솔");
		return "hello";
	}
	
	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
		model.addAttribute("name", name);
		return "hello-template";
	}
	
	// 문자 그대로
	@GetMapping("hello-string")
	@ResponseBody
	public String helloString(@RequestParam(value = "name", required = false) String name) {
		
		return "hello " + name;
	}
	
	// data 전달
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloAPI(@RequestParam(value = "name", required = false) String name) {
		
		Hello hello = new Hello();
		hello.setName(name);
		
		return hello;
	}
	
	static class Hello {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	// git push test용 

}