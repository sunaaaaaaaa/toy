package com.kh.toy.member.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.member.model.dao.MemberDao;
import com.kh.toy.member.model.service.MemberService;

public class JoinForm {
	
	private String userId;
	private String password;
	private String email;
	private String tell;
	private HttpServletRequest request;
	
	private MemberService memberService = new MemberService();
	private Map<String,String>faildValidation = new HashMap<String,String>(); //validation 실패한 데이터 여기다 담음
	
	//검증해야할 데이터 초기화 작업
	public JoinForm (ServletRequest request) {
		this.request = (HttpServletRequest)request;
		this.userId = request.getParameter("userId");
		this.password = request.getParameter("password");
		this.email = request.getParameter("email");
		this.tell = request.getParameter("tell");
	}
	
	public boolean test() {
		
		boolean isFailed = false;
		
		//사용자 아이디가 DB에 이미 존재하는지 확인
		if(userId.equals("") || memberService.selectMemberBYId(userId) !=null) {
			faildValidation.put("userId",userId);
			isFailed = true;
		}
		
		//비번이 영여,숫자,특수문자 조합의 8자리 이상 문자열인지 확인
		if(!Pattern.matches("(^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$)", password)) {
			faildValidation.put("password",password);
			isFailed = true;
		}
		
		//전화번호가 숫자로만 이루어져 있는지 확인
		if(!Pattern.matches("\\d{9,11}", tell)) {
			faildValidation.put("tell",tell);
			isFailed = true;
		}
		
		if(isFailed) {
			request.getSession().setAttribute("joinValid", faildValidation);
			request.getSession().setAttribute("joinForm",this);
			return false;
		}else {
			request.getSession().removeAttribute("joinForm");
			request.getSession().removeAttribute("joinValid");
			return true;
		}
	}

	
	
	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getTell() {
		return tell;
	}

	
	
	
	
}
