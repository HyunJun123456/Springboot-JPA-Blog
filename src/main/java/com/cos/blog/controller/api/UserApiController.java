package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired // spring bean에 메모리를 등록해줌
	private UserService userService;
	

	
	@PostMapping("/auth/joinProc") // 회원가입
	public ResponseDTO<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController: save 호출됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
		int result = userService.memberJoin(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), result); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
	}
	
	// 스프링 시큐리티 이용해서 로그인으로 변경예정
	/*
	 * @PostMapping("/api/user/login") public ResponseDTO<Integer>
	 * login(@RequestBody User user, HttpSession session){
	 * System.out.println("UserApiController: login 호출됨"); User principal =
	 * userService.login(user); // principal (접근주체) if(principal != null) {
	 * session.setAttribute("principal", principal); } return new
	 * ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴
	 * (Jackson) }
	 */
	
	@PutMapping("/user") // key = value, x-www-form-uleencoded만 받는다면 RequestBody는 필요없음.
	public ResponseDTO<Integer> update(@RequestBody User user){ // RequestBody가 없으면 json 형태로 받을 수 없음
		userService.update(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 떄문에 우리가 직접 세션갑을 변경 예정임.
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
		
		
	}
	
}
