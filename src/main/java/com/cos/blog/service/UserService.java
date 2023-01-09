package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. 메모리를 대신 띄워준다.
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
@Service
public class UserService {
	/*
	 * 서비스가 필요한 이유
	 * 1. 트랜잭션 관리
	 * 2. 서비스의 의미를 알아야함 -> 2개 이상의 서비스를 해줘야 할 수 있음
	 * 여러개의 트랜잭션이 하나의 서비스로 뭉칠 수 있음
	 * */
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public int memberJoin(User user) {
		try {
			String rawPassword =  user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			user.setPassword(encPassword);
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입(): "+e.getMessage());
		}
		return -1;
	}

	/*
	 * @Transactional(readOnly = true) // select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
	 * 유지 가능) public User login(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
	
	
}
