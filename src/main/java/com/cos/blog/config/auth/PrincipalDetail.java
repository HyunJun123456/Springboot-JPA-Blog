package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;
// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails{
	private User user; // 콤포지션(객체를 품고 있음)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	// 계정이 만료되지 않았는지 리턴한다. (true: 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true; // false가 되면 해당 유저는 계정 만료 상태가 됨
	}

	// 계정이 잠겨있지 않았는지 리턴한다. (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true; // false면 계정이 만료되서 로그인이 되지 않음
	}

	// 비밀번호가 만료되지 않았는지 리턴한다. (true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화(사용가능)인지 리턴한다. (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	// 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*
		 * collectors.add(new GrantedAuthority() {// 자바는 메서드를 넘길 수 없음
		 * 
		 * @Override public String getAuthority() { return "ROLE_"+user.getRole(); //
		 * role을 받을 때 꼭 써줘야함 => ROLE_ } });
		 */
		
		collectors.add(()->{ // 람다식으로 표현 가능
			return "ROLE_"+user.getRole();
		});
		
		
		return collectors;
	}
}
