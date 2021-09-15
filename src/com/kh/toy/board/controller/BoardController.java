package com.kh.toy.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.toy.board.model.dto.Board;
import com.kh.toy.board.model.service.BoardService;
import com.kh.toy.common.exception.PageNotFoundException;
import com.kh.toy.common.file.FileDTO;
import com.kh.toy.common.file.FileUtil;
import com.kh.toy.common.file.MultiPartParams;
import com.kh.toy.member.model.dto.Member;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String[] uriArr = request.getRequestURI().split("/");
		
		switch (uriArr[uriArr.length-1]) {
		case "board-form":
			boardForm(request,response);
			break;
		case "upload":
			upload(request,response);
			break;
		case "board-detail":
			boardDetail(request,response);
			break;

		default: throw new PageNotFoundException();
		
		}
	}

	private void boardDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//bdIdx로 게시글 상세 데이터 조회
		String bdIdx = request.getParameter("bdIdx");
		Map<String,Object> datas = boardService.selectBoardDetail(bdIdx);
		request.setAttribute("datas", datas);
		request.getRequestDispatcher("/board/board-detail").forward(request, response);
		
		
		
		
	}

	private void upload(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		// 1. multipartRequest 방법
		 /* MultipartRequest multipartRequest = new MultipartRequest(request,
		 * "C:\\CODE\\upload"); String title = multipartRequest.getParameter("title");
		 * String content = multipartRequest.getParameter("content");
		 * 
		 * System.out.println("제목 : "+ title); System.out.println("본문 : "+ content);
		 */
		
		// 2. multipartParser 방법
		FileUtil util = new FileUtil();
		MultiPartParams params = util.fileUpload(request);
		
		//게시물저장을 위해 Board DTO생성
		/*
		 * Board board = new Board(); board.setTitle(params.getParameter("title"));
		 * board.setContent(params.getParameter("content")); 
		 * System.out.println(board);
		 * List<FileDTO> fileDTOs = params.getFileInfo(); 
		 * for (FileDTO fileDTO :fileDTOs) { System.out.println(fileDTOs); }
		 */
		
		// 3. 우리가만든 MultipartParams로 하기
		Member member = (Member) request.getSession().getAttribute("authentication");
		Board board = new Board();
		board.setUserId(member.getUserId()); //권한열어주기
		board.setTitle(params.getParameter("title"));
		board.setContent(params.getParameter("content"));
		
		List<FileDTO> fileDTOs = params.getFilesInfo(); //service로 넘겨야할 데이터를 다 받아왔습니당
		boardService.insertBoard(board,fileDTOs);
		response.sendRedirect("/"); //index로 redirect
		
	}

	private void boardForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.getRequestDispatcher("/board/board-form").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
