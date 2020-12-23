<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp" %>
<h1>Login Page</h1>

<!-- 로그인은 post값으로 지정하는 것이 규칙이다.
	왜? 로그인은 get도 post도 아니기 때문이다.  -->
<hr/>
<form action="/hello/user?gubun=loginProc" method="post">
	<input type="text" name="username" placeholder="username"/>
	<input type="password" name="password" placeholder="password"/>
	<button>로그인</button>
</form>
</body>
</html>