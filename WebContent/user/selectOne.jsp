<%@page import="com.cos.hello.model.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp" %>
<h1>User Info</h1>
<%
	String result = (String) request.getAttribute("result");
%>
<%=result %>

<!-- 맞는 코드인데 오류가 난다면 툴 버그여서 지우고 저장 후 다시 작성. -->
<h1>${result}</h1>

</body>
</html>