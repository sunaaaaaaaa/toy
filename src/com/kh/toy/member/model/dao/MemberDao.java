package com.kh.toy.member.model.dao;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kh.toy.common.db.JDBCTemplate;
import com.kh.toy.common.exception.DataAccessException;
import com.kh.toy.member.model.dto.Member;

public class MemberDao {

	JDBCTemplate jdbcTemplate = JDBCTemplate.getInstance();
	
	public MemberDao() {
	}
	
	public Member memberAutenticate(String userId, String password, Connection conn) {
		
		Member member = null;
		PreparedStatement pstm =null;  
		ResultSet rset =null; 
		
		try {
				String query = "select * from member where user_id = ? and password = ? ";
				pstm = conn.prepareStatement(query);
				pstm.setString(1, userId);
				pstm.setString(2, password);
				rset = pstm.executeQuery();
			
				String[] fieldArr = {"user_id","password","email","tell","grade","is_leave","reg_date","rentable_Date"};
				if(rset.next()) {
					member = convertRowToMember(fieldArr,rset);
				}
				System.out.println("조회된 회원 정보 \n"+member);
				
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			jdbcTemplate.close(pstm,rset);
		}
		return member;
	}
	
	
	public Member selectMemberById(String userId, Connection conn){
		
		Member member = null;
		PreparedStatement pstm =null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM MEMBER WHERE USER_ID = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, userId);
			rset = pstm.executeQuery();
			
			if(rset.next()) {
				member = convertRowToMember(rset);
			}
		   }catch (SQLException e) {
			 throw new DataAccessException(e);  //필요한시점에만 예외처리할수있도록..
	       }finally {
			jdbcTemplate.close(pstm, rset);
		}
		return member;
	}
	
	
	public List<Member> selectMemberList(Connection conn){
		
		List<Member> memberList = new ArrayList<Member>();
		PreparedStatement pstm = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM MEMBER";
			pstm = conn.prepareStatement(query);
			rset = pstm.executeQuery();
			while(rset.next()) {
				Member member = convertRowToMember(rset);
				memberList.add(member);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			jdbcTemplate.close( pstm, rset);
		}
		return memberList;
	}
	
	
	public int insertMember(Member member, Connection conn) { //예외나면 service클래스로 던짐
		int res=0;
		PreparedStatement pstm =null;
		
		try {
			String query = "INSERT INTO MEMBER(USER_ID,PASSWORD,EMAIL,TELL) VALUES(?,?,?,?) ";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, member.getUserId());
			pstm.setString(2, member.getPassword());
			pstm.setString(3, member.getEmail());
			pstm.setString(4, member.getTell());
			res = pstm.executeUpdate();
			System.out.println(res);
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		} finally {
			jdbcTemplate.close(pstm);
		}
		return res;
	}

	
	public int updatePassword(String userId, String password, String newpassword, Connection conn) {
		int res =0;
		PreparedStatement pstm = null;
		
		try {
			String query = "UPDATE MEMBER SET PASSWORD = ? WHERE USER_ID =? AND PASSWORD =?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, newpassword);
			pstm.setString(2, userId);
			pstm.setString(3, password);
			res = pstm.executeUpdate();
			System.out.println(res);
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally {
			jdbcTemplate.close(pstm);
		}
		return res;
	}
	
	
	public int deleteMember(String deleteId, Connection conn) {
		int res =0;
		PreparedStatement pstm = null;
		
		try {
			String query = "DELETE FROM MEMBER WHERE USER_ID = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, deleteId);
			res = pstm.executeUpdate();
			
		} catch (SQLException e) {
           throw new DataAccessException(e);
		}finally {
			jdbcTemplate.close(pstm);
		}
		return res;
	}
	

	
	private Member convertRowToMember(ResultSet rset) throws SQLException{
		
		Member member = new Member();
		member.setUserId(rset.getString("user_id"));
		member.setPassword(rset.getString("password"));
		member.setEmail(rset.getString("email"));
		member.setGrade(rset.getString("grade"));
		member.setIsLeave(rset.getInt("is_leave"));
		member.setRegDate(rset.getDate("reg_date"));
		member.setRentableDate(rset.getDate("rentable_Date"));
		member.setTell(rset.getString("tell"));
		
		return member;
	}

	private Member convertRowToMember(String[] columns, ResultSet rset) throws SQLException{
		
		Member member = new Member();
		for (int i = 0; i < columns.length; i++) {
			String column = columns[i].toLowerCase();
			
			switch (column) {
			case "user_id": member.setUserId(rset.getString("user_id"));	break;
			case "password" : member.setPassword(rset.getString("password")); break;
			case "email" : member.setEmail(rset.getString("email")); break;
			case "grade" : member.setGrade(rset.getString("grade")); break;
			case "is_leave" : member.setIsLeave(rset.getInt("is_leave")); break;
			case "reg_date" : member.setRegDate(rset.getDate("reg_date")); break;
			case "rentable_Date" : member.setRentableDate(rset.getDate("rentable_Date")); break;
			case "tell" : member.setTell(rset.getString("tell")); break;
			}
		}
		return member;
	}
}
