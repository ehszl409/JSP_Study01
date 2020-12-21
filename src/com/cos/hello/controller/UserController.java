package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.ServletException;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리.
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		// URI기법 파일명을 통해서 찾아가는 것.
		// 라우터
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");
		} else if (gubun.equals("selectOne")) {
			resp.sendRedirect("user/selectOne.jsp");
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp");
		}

		// 니가 요청을 하면 한 번더 요청을 해준다.
		// resp.sendRedirect("auth/join.jsp");
		// resp.sendRedirect("auth/login.jsp");
	}
}
