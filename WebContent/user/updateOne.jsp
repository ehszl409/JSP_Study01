<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp" %>
<h1>회원정보수정</h1>
<form action="user?gubun=updateProc" method="post">
	<table border="1">
		<tr>
			<th>번호</th>
			<th>유저네임</th>
			<th>패스워드</th>
			<th>이메일</th>
		</tr>
		<tr>
			<td>
			<input type="hidden" name="id" value="${user.id}"/>
			${user.id}
			</td>
			<td>${user.username}</td>
			<td>
				<input type="password" name="password" value="${user.password}"/>
			</td>
			<td>
				<input type="email" name="email" value="${user.email}"/>
			</td>
		</tr>
	</table>
	<button>수정</button>
</form>

</body>
</html>