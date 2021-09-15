package com.kh.toy.member.model.dto; //저장용

import java.sql.Date;



public class Member {
	
	
	private String userId;
	private String password;
	private String email;
	private String grade;
	private String tell;
	private Date rentableDate;
	private Date regDate;
	private int isLeave;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public Member(String userId, String password, String email, String grade, String tell, Date rentableDate,
			Date regDate, int isLeave) {
		super();
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.grade = grade;
		this.tell = tell;
		this.rentableDate = rentableDate;
		this.regDate = regDate;
		this.isLeave = isLeave;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrade() {

		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public Date getRentableDate() {
		return rentableDate;
	}

	public void setRentableDate(Date rentableDate) {
		this.rentableDate = rentableDate;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(int isLeave) {
		this.isLeave = isLeave;
	}

	@Override
	public String toString() {
	
		
		return "아이디:" + userId + ", 비번:" + password + ", e-메일:" + email + ", 등급:" +grade
				+ ", 전번:" + tell + ", 등록일:" + rentableDate + ", 대출가능일:" + regDate + ", 탈퇴:" + isLeave;
	}
	
	
	
	
	
}
