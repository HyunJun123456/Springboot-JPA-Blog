package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

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

	// @GetMapping("/user/join")

	@GetMapping("/user/updateForm")
	public String updateForm() {

		return "user/updateForm";
	}
	/*
	 * 로그인 요청 -> 인증필터 (유저 패스 인증 토큰) -> AuthenticationManager -> UserDetailService ->
	 * DB 세션에 시큐리티 컨텍스트에 Authentication 객체를 저장 가능
	 */

	@GetMapping("/auth/kakao/callback") // code를 받으면 응답이 된거임.
	public @ResponseBody String kakaoCallback(String code) {// Data를 리턴해주는 컨트롤러 함수
		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)

		// Retrofit2: 안드로이드에서 많이씀.
		// OkHttp
		// RestTemplate
		RestTemplate rt = new RestTemplate();
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "0995812abee8d29ce73b3bfb49440747");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = // body값과 header값을 가진 데이터가 됨.
				new HttpEntity<>(params, headers);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰: " + oauthToken.getAccess_token());

		RestTemplate rt2 = new RestTemplate();
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기.
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = // body값과 header값을 가진 데이터가 됨.
				new HttpEntity<>(headers2);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest2, String.class);

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper2 = new ObjectMapper();
		objectMapper2.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); // 네이밍 전략 추가 (Snake -> Camel)
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+kakaoProfile.getKakaoAccount().getEmail());
		
		return response2.getBody();
	}

}
