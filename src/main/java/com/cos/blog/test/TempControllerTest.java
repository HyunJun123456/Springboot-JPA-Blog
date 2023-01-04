package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 파일을 return함.
public class TempControllerTest {

	// http://localhost:8000/blog/temp/home
	// 404 에러 -> 페이지를 찾지 못함
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로: src/main/resources/static
		// 리턴명: /home.html
		// 풀경로: src/main/resources/static/home.html
		return "/home.html";
	}
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/a.jpg";
	}
	
	@GetMapping("/temp/jsp") 
	public String tempJsp() { // tomcat에서 compile해줘야함.
		// prefix: /WEB-INF/views/
		// suffix: .jsp
		// 풀네임: /WEB-INF/views/test.jsp
		return "test"; // 동적인 파일이라서 찾을 수가 없음
	}
	
}
