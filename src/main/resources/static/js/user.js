let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{ // function(){} , ()=>{} this를 바인딩하기 위해서!!
			this.save(); // function을 쓰면 this는 window 객체에서 가져옴.
		});
		/*$("#btn-login").on("click", ()=>{ // function(){} , ()=>{} this를 바인딩하기 위해서!!
			this.login(); // function을 쓰면 this는 window 객체에서 가져옴.
		});*/
		$("#btn-update").on("click", ()=>{ // function(){} , ()=>{} this를 바인딩하기 위해서!!
			this.update(); // function을 쓰면 this는 window 객체에서 가져옴.
		});
	},
	
	save: function(){ // 회원가입
		//alert("user의 save 함수 호출됨");
		let data ={
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		// ajax 호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청.
		// ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌.
		$.ajax({// 회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // JSON 문자열이 됨. // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){// 응답의 결과가 파라미터로 담길 수 있음
			console.log(resp)
			alert("회원가입이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 

	},
	
	/*login: function(){
		let data ={
			username: $("#username").val(),
			password: $("#password").val(),
		};
		

		$.ajax({
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), // JSON 문자열이 됨. // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){// 응답의 결과가 파라미터로 담길 수 있음
			console.log(resp)
			alert("로그인이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 

	}*/
	update: function(){ // 회원수정
		let data ={
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({// 회원수정 수행 요청
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), // JSON 문자열이 됨. // http body 데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){// 응답의 결과가 파라미터로 담길 수 있음
			console.log(resp)
			alert("회원수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 

	}
	
	
	
	
}

index.init();