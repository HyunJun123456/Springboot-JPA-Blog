
package com.cos.blog.model;

import javax.annotation.Generated;

import lombok.Data;
@Data
@Generated("jsonschema2pojo")
public class KakaoProfile {
	public Long id;
	public String connectedAt;
	public Properties properties;
	public KakaoAccount kakaoAccount;

	@Data
	@Generated("jsonschema2pojo")
	public class Properties {
		public String nickname;
	}

	@Data
	@Generated("jsonschema2pojo")
	public class KakaoAccount {
		public Boolean profileNicknameNeedsAgreement;
		public Profile profile;
		public Boolean hasEmail;
		public Boolean emailNeedsAgreement;
		public Boolean isEmailValid;
		public Boolean isEmailVerified;
		public String email;

		@Data
		@Generated("jsonschema2pojo")
		public class Profile {
			public String nickname;
		}
	}
}
