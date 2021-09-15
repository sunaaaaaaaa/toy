<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<style type="text/css">
	h1{
		text-align:center;
	}

</style>
</head>
<body>

<h1>HEllO</h1>

<!-- 만약 인증정보가 있다면 -->
<c:if test='${empty authentication}'>
<h2><a href='/member/login-form'>로그인</a></h2>
<h2><a href='/member/join-form'>회원가입</a></h2>
</c:if>

<!-- 없다면 -->
<c:if test='${not empty authentication}'>
<h2><a href='/member/logout'>logout</a></h2>
<h2><a href='/member/mypage'>마이페이지</a></h2>
<h2><a href='/board/board-form'>게시글작성</a></h2>
</c:if>

</body>
</html>