package com.kh.toy.member.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.common.exception.HandlableException;
import com.kh.toy.common.http.HttpConnector;
import com.kh.toy.common.http.RequestParams;
import com.kh.toy.common.mail.MailSender;
import com.kh.toy.member.model.dao.MemberDao;
import com.kh.toy.member.model.dto.Member;


public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	private JDBCTemplate template = JDBCTemplate.getInstance();
	

	public Member memberAuthentiate(String userId, String password) {

		//1.사용자의 아이디로 DB에서 암호화된 패스워드를 가져와, password를 복호화한다... (암호화되어서 db에 들어가야함)
		//2.요청 전달한 아이디와 패스워드와, 복호화된 아이디 패스워드가 일치하는지 확인
		//3.로그인에 성공할경우 DB에서 해당회원의 즐겨찾기 정보를 읽어와, session에 저장
		//..등ㄷ.ㅇ
		Member member =null;
		Connection conn = template.getConnection(); //커넥션 관리 여기서 해줌 
		
		try {
			member = memberDao.memberAutenticate(userId,password,conn);
			System.out.println(member);
		}finally {
			template.close(conn);
		}
		return member;
	}
	

	public Member selectMemberBYId(String userId) {
		Member member =null;
		Connection conn = template.getConnection();
		try {
			member = memberDao.selectMemberById(userId,conn);
		}finally {
			template.close(conn);
		}
		return  member;
	}
	
	public List<Member> selectMemberList() {
		
		Connection conn =template.getConnection();
		List<Member>  memberList = null;
		
		try {
			 memberList = memberDao.selectMemberList(conn); 
		}finally {
			template.close(conn);
		}
		return memberList;
			
	}
	
	public int insertMember(Member member) {
		Connection conn = template.getConnection();
		int res=0;
		
		try {
			//회원가입처리
			res = memberDao.insertMember(member,conn);
			//회원가입 이후 자동 로그인처리
			//방금 가입한 회원의 아이디로 정보를 다시 조회
			//memberDao.selectMemberById(member.jetUserId());
			//(다오를통해)사용자 정보를 받아서.. 해당정보로 로그인처리 진행
			template.commit(conn); //commit처리
		}catch(Exception e) {
			template.rollback(conn);
			//e.printStackTrace();
			//여기서 exception처리해버리니까 handler로 처리하도록 함
			throw e;
		}finally {
			template.close(conn);
		}
		return res;
	}
	
	
	
	public int updatePassword(String userId, String password,String newpassword) {
		Connection conn = template.getConnection();
		int res = 0;
		try {
			res = memberDao.updatePassword(userId,password,newpassword,conn);
			template.commit(conn);
		} catch (Exception e) {
			template.rollback(conn);
			e.printStackTrace();
		}finally {
			template.close(conn);
		}
		return res;
	}

	
	
	public int deleteMember(String deleteId) {
		int res =0;
		Connection conn = template.getConnection();
		try {
			res = memberDao.deleteMember(deleteId,conn);
			template.commit(conn);
		}catch(Exception e) {
			template.rollback(conn);
			e.printStackTrace();
		}finally {
			template.close(conn);
		}
		return res;
	}


	public void authenticateEmail(Member member,String persistToken) {

		HttpConnector conn = new HttpConnector();
		String queryString = conn.urlEncodedForm( RequestParams.builder()
													.param("mail-template", "join-auth-email")
													.param("persistToken", persistToken)
													.param("userId", member.getUserId())
													.build()); 
		
		String mailTemplate = conn.get("http://localhost:7070/mail?"+queryString);
		MailSender sender = new MailSender();
		sender.sendEmail(member.getEmail(), "환영합니다. " + member.getUserId() + "님", mailTemplate);

	}
}
