package com.kh.toy.common.exception;

import com.kh.toy.common.code.ErrorCode;

//예외처리가 강제되지 않은 UnCheckedException
//Dao에서 SQL Exception대신 DataAcceessException을 반환해서, 
//Service단에서 예외처리가 강제되는것을 방지함
//안그럼 모든 클래스에서 SQLexception을 처리하고있어야함
public class DataAccessException extends HandlableException { //runtime대신 핸들러블 Exception 상속
	
	private static final long serialVersionUID = 521587827126031031L;

	public DataAccessException(Exception e) {
		super(ErrorCode.DATABSE_ACCESS_ERROR,e);
		
	}
	
	
	
	
	
	
	
	
	
	
}
