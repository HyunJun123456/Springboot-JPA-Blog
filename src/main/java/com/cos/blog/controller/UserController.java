package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 /이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	//@GetMapping("/user/join")
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		
		return "user/updateForm";
	}
	/*로그인 요청 -> 인증필터 (유저 패스 인증 토큰) -> AuthenticationManager -> UserDetailService -> DB
	 * 세션에 시큐리티 컨텍스트에 Authentication 객체를 저장 가능
	 * */
	
	
}
