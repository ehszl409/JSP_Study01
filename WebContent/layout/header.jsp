<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 내부 객체
	// 내가 그냥 적을 수 있는 객체
	// request,response,out(response로 부터 만들어진 PrintWrite),session;
	String contextPath = request.getContextPath();
%>  
<%=contextPath %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>URL방식 안됨!! URI방식으로 요청하기!!!</h1>
<ul>
	<li><a href="<%=contextPath%>/user?gubun=login">로그인</a></li>
	<li><a href="<%=contextPath%>/user?gubun=join">회원가입</a></li>
	<li><a href="<%=contextPath%>/user?gubun=selectOne">유저정보보기</a></li>
	<li><a href="<%=contextPath%>/user?gubun=updateOne">유저수정하기</a></li>
	<li><a href="<%=contextPath%>/board?gubun=deleteOne">게시글삭제하기</a></li>
	<li><a href="<%=contextPath%>/board?gubun=insertOne">게시글입력하기</a></li>
	<li><a href="<%=contextPath%>/board?gubun=selectAll">게시글전체보기</a></li>
	<li><a href="<%=contextPath%>/board?gubun=updateOne">게시글수정하기</a></li>
</ul>