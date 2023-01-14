package com.info;

public class Review {
	/*
	 * 스프링작동원리 복습
	 * 톰캣 시작 -> 필터 -> 디스패쳐 -> controller -> service -> JPA repository
	 * -> 영속성 컨텍스트 - Datasource - DB
	 * VIew Resolver / 세션 / 인터셉터
	 * request => http://localhost:8000/login (post) 요청
	 * Body에 username, password
	 * 디스패쳐가 컨틀롤러를 메모리에 띄워야함
	 * 어떤 컨트롤러를 띄워야하는지? 
	 * 처음에는 영속성 컨텍스트에 영속성이 없을꺼임 그래서 DB에 질의 요청을 함.
	 * 그 후에 영속성 오브젝트가 생성됨.
	 * 세션에 정보를 등록해둔다.
	 * 컨트롤러가 restController면 데이터를 응답함. (메시지 자체를 응답해줌)
	 * 컨트롤러가 Controller면 html을 응답함.
	 * -> viewResolver가 작동해서 html을 찾아줌.
	 * 인터셉터: 함수가 실행되기 직전에 권한 체크를 먼저 할 수 있음. (가로채는거임)
	 * 필터와는 조금 다른 개념임 (필터는 걸러내는거니깐)
	 * 트랜잭션이 종료될 때 커밋이 시작됨.
	 * 서비스는 하나의 기능을 담당하고 여러번의 데이터베이스를 요청 할 수 있다.
	 * */
}
