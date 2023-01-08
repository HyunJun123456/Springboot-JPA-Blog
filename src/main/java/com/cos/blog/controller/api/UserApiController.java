package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/api/user")
	public ResponseDTO<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController: save 호출됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 됨.
		user.setRole(RoleType.USER);
		int result = userService.memberJoin(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), result); // 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson)
	}
	
}
