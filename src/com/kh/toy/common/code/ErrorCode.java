package com.kh.toy.common.code;

public enum ErrorCode {
	
	DATABSE_ACCESS_ERROR("(enum코드) ab접근중 에러가 발생하엿습니당!"),
	FAILED_VALIDATED_ERROR("(enum코드) 데이터 양식이 적합하지 않음!"),
	MAIL_SEND_FAIL_ERROR("(enum코드) 메일 보냄에 실패함!실패!실패!"), 
	HTTP_CONNECT_ERROR("(enum코드) 통신연결에 실패실패~!"), 
	AUTHENTICATION_FAILED_ERROR("(enum코드) 유효하지안흔ㄴ 인증입니다"),
	UNAUTHORIZED_PAGE("(enum코드) 접근권한이 업읍ㄴ디ㅏㅇ"),
	REDIRECT_TO_LOGIN_PAGE("","/member/login-form"),
	FAILED_FILE_UPLOAD_ERROR("(enum코드)파일업로드에 실패했습니다");
	
	public final String MESSAGE;
	public final String URL;
	
	//메세지만 받을 친구
	ErrorCode(String msg){
		this.MESSAGE = msg;
		this.URL = "/index";
	}
	
	//메세지랑 url을 받을 친구
	ErrorCode(String msg,String url){
		this.MESSAGE = msg;
		this.URL = url;
	}
	
	
	
	

}
