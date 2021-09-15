package com.kh.toy.common.filter;

import java.io.IOException;

import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.toy.member.validator.JoinForm;


public class ValidatorFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ValidatorFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		//request에는 url가 없
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String[] uriArr = httpRequest.getRequestURI().split("/");
		
		if(uriArr.length != 0) { //validate 해야하는것 : 수정,삭제가 일어나는 데이터들
			
			String redirectURI =null;
			
			switch (uriArr[1]) {
			case "member":redirectURI = memberValidation(httpRequest,httpResponse,uriArr);
				break;
			default:   
				break;
			}
		
		if(redirectURI != null) {
			httpResponse.sendRedirect(redirectURI);
			return;
			}
		}
		chain.doFilter(request, response);
	}

	private String memberValidation(HttpServletRequest httpRequest,HttpServletResponse httpResponse, String[] uriArr) {
		
		String redirectURI = null;
		
		switch (uriArr[2]) {
		case "join": //회원가입시 입력한 정보를 검증을함
			JoinForm joinForm = new JoinForm(httpRequest);
			if(!joinForm.test()) {
				redirectURI = "/member/join-form?err=1"; 
			}
			break;

		default:
			break;
		}
	
		return redirectURI;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
