package com.kh.toy.common.code;

public enum Config {
	
	//DOMAIN("나중에 배포할 도메인"),
	DOMAIN("http://local:7070"),
	SMTP_AUTHENTICATION_ID("tjsdk705@gmail.com"),
	SMTP_AUTHENTICATION_PASSWORD("1234"),
	COMPANY_EMAIL("tjsdk705@gmail.com"),
	//UPLOAD_PATH("C:\\CODE\\upload\\"), //운영서버용
	UPLOAD_PATH("C:\\CODE\\upload\\"); //개발서버용
	
	
	public final String DESC;
	
	private Config(String desc) {
		this.DESC =desc;
	}
	
	
	

}
