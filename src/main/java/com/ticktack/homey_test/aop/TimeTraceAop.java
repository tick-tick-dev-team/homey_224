package com.ticktack.homey_test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component // spring bean으로 등록
public class TimeTraceAop {
	
	@Around("execution(* com.ticktack.homey_test..*(..))") // 패키지 모든 곳에 적용 가능 (aop around로 검색)
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		System.out.println("START : " + joinPoint.toString());
		// START : execution(List org.springframework.data.jpa.repository.JpaRepository.findAll())
		
		try {
			return joinPoint.proceed();
		} finally {
			long finish = System.currentTimeMillis();
			long timeMs = finish - start;
			System.out.println("END : " + joinPoint.toString() + "  " + timeMs + "ms");
			// END : execution(String com.ticktack.homey_test.Controller.MemberController.list(Model))  222ms
		}
	}

}
