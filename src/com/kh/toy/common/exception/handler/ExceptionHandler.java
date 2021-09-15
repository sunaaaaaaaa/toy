package com.kh.toy.common.exception.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.toy.common.exception.HandlableException;

/**
 * Servlet implementation class ExceptionHandler
 */
@WebServlet("/exception-handler")
public class ExceptionHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExceptionHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//서블릿 컨테이너는 HandlableException이 발생하면, 요청을 HandlableException으로 재지정
		//이때 ExceptionHandler 서비스 메소드로 넘겨주는 request의 속성(javax.servlet.error.exception)에 
		//발생한 예외 객체를 함께 넘겨줌

		HandlableException e =(HandlableException) request.getAttribute("javax.servlet.error.exception");
		
		request.setAttribute("msg", e.error.MESSAGE); //Enum에서 넘겨줌
		request.setAttribute("url", e.error.URL); // 원하는 경로 넣을수도 있고,
		// request.setAttribute("back", "s"); 뒤로가기 할수도 있음
		request.getRequestDispatcher("/WEB-INF/views/error/result.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
