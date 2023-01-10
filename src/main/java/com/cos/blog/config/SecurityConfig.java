package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것.
@Configuration // 빈이 등록됨. (IoC 관리)
@EnableWebSecurity // 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다. -> 시큐리티 필터가 등록이 됨.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC가 됨! -> spring이 관리하기 시작함.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
													// 이걸 안넣어주면 패스워드 비교가 안됨.
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http // csrf 토큰이 없기 때문에 막혀버림
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeRequests()	
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**") // auth는 누구나 들어올 수 있다.
				.permitAll()
				.anyRequest() // 다른 요청으로 들어오면
				.authenticated() // 인증이 되야해
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
				.defaultSuccessUrl("/");
	}
}
