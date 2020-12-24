package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리.
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBConn;
import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.service.UsersService;

// 디스패쳐의 역할 = 분기 = 필요한 view를 응답해주는것.
public class UserController extends HttpServlet {

	// req와 res는 톰켓이 만들어준다. (클라이언트의 요청이 있을때 마다)
	// req는 BufferedReader할 수 있는 ByteStream
	// res는 BufferedWrite할 수 있는 ByteStream

	// http://localhost:8000/hello/front
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FrontController 실행됨.");

		// ?뒤의 값을 파싱해준다.
		// http://localhost:8000/
		String gubun = req.getParameter("gubun"); // /hello/front
		System.out.println(gubun);
		route(gubun, req, resp);
	}
	
	//  throws IOException 함수 전체를 try / catch로 묶는다.
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// UsersService을 한번만 호출하고 사용하면 된다.
		UsersService usersService = new UsersService();
		
		// URI기법: 파일명을 통해서 찾아가는 것.
		// 라우터
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");
		} else if (gubun.equals("selectOne")) { // 유저 정보 보기. (인증이 필요한 이미지)
			usersService.유저정보보기(req, resp);
		} else if (gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(req, resp);
		} else if (gubun.equals("joinProc")) { // 회원가입을 수행하는 if
			usersService.회원가입(req, resp);
    	} else if (gubun.equals("loginProc")) {
			// SELECT id, username, email FROM users WHERE username = ? AND password = ?
			// DAO의 함수명 : login(),  return Users 오브젝트
			// 정상 : 세션에 Users 오브젝트 담고 index.jsp
			// 비정상 :  login.jsp
			usersService.로그인(req, resp);		
		} else if (gubun.equals("updateProc")) {
			usersService.회원수정(req, resp);		
		} else if (gubun.equals("deleteProc")) {
			usersService.회원삭제(req, resp);		
		}
	}
}
