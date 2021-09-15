package com.kh.toy.common.exception;

import com.kh.toy.common.code.ErrorCode;

public class HandlableException  extends RuntimeException {
	
	private static final long serialVersionUID = 521587827126031031L;
	
	//Handlable Exception 용도 
	//1.에러코드 enum을 받아줌
	//2.log를 남기지 않고, 사용자에게 알림만 전달하는 용도의 생성자
	public ErrorCode error;
	
	public HandlableException(ErrorCode error) {
		this.error = error;
		this.setStackTrace(new StackTraceElement[0]); //stackTrace를 비워준다
	}
	
	//반대케이스
	//매개변수가 2개인 생성자 : 알람 안남기고, 에러코드만 넘겨줌
	public HandlableException(ErrorCode error,Exception e) {
		this.error = error;
		e.printStackTrace();
		this.setStackTrace(new StackTraceElement[0]); //stackTrace를 비워준다
	}
	
}
