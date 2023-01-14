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
	
	@Transactional
	public void update(User user) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		// 회원수정 함수 종료시 => 서비스 종료 => 트랜잭션 종료 => commit이 자동으로 됨.
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
		
	}

	/*
	 * @Transactional(readOnly = true) // select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
	 * 유지 가능) public User login(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */
	
	
}
