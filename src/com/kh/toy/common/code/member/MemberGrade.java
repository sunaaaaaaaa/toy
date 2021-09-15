package com.kh.toy.common.code.member;

//enum(enumerated type) : 열거형
//서로 연관된 상수들의 집합
//상수들을 하나의 묶음으로 다루기 위한 enum만의 문법적 형식과 메소드를 제공해줌
//https://techblog.woowahan.com/2527/   enum활용기 우아한형제들
public enum MemberGrade {

	//내부적으로 enum은 class임
	//회원등급코드가 'ME00'이면 등급명은 '일반'이고 연장가능횟수 1회  --> 상수들이 연관되어있음
	ME00("일반", "user", 1),
	ME01("성실", "user", 2),
	ME03("우수", "user", 3),
	ME04("vip",  "user", 4),

	AD00("super", "admin"),
	AD01("member","admin"),
	AD02("board", "admin");
	
	public final String DESC;
	public final String ROLE;
	public final int EXTENDABLE_CNT;
	
	private MemberGrade(String desc, String role) {
		this.DESC = desc;
		this.ROLE = role;
		this.EXTENDABLE_CNT = 9999;
	}
	
	private MemberGrade(String desc, String role, int extendableCnt) {
		this.DESC = desc;
		this.ROLE = role;
		this.EXTENDABLE_CNT = extendableCnt;
	}
	
	
}
