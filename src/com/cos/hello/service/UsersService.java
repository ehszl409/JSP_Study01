package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.utill.Script;

// 이름 규칙 : 테이블 명 서비스내용 Service 
// 서비스는 연산을 하는 공간.
public class UsersService {
	
	// 회원가입을 하기 위한 로직이 모두 들어가 있다.
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 데이터 원형 username=PARK&password=123456789&email=PARK@naver.com

		// 1. form의 input태그에 있는 3가지 값 username, password, email 받기
		// getParameter()는 데이터를 가져 오는 것이 아니라 특정한 형식의 데이터를
		// 파싱해주는 역할을 하는 함수이다. 데이터는 req(request)가 이미 들고 있다.
		// 사용자에게 데이터를 받는 가장 쉬운 방법은 form태그에 받는 것이 가장 이상적이다.
		// 그리고 get의 방식이나 post방식의 데이터를 모두 받을 수 있다.
		// 단 post방식 중 데이터의 형식이 x-www-form-urlencoded형식의 데이터만 받을 수 있다
		// x-www-form-urlencoded는 이름=데이터 형식의 데이터다.
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		System.out.println("==========joinProc Start==========");
		System.out.println(username);
		System.out.println(password);
		System.out.println(email);
		System.out.println("==========joinProc End==========");

		// 2. DB에 연결해서 3가지 값을 INSERT 하기

		Users user = Users.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();

		// 싱글톤 패턴으로 바꿔보자.
		UsersDao usersDao = new UsersDao();
		int result = usersDao.insert(user);
		

		// 분기 = 컨트롤러의 책임.
		if(result == 1) {
			// 3. INSERT가 정상적으로 되었다면 auth/login.jsp응답
			Script.href(resp, "index.jsp", "회원가입 성공");
		} else {
			Script.back(resp, "회원가입 실패");
		}
		
	}
	
	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 아이디와 비밀번호를 서버에서 맞는지 비교해야한다.
		// 1. 값 전달 받기.
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println("==========loginProc Start==========");
		System.out.println(username);
		System.out.println(password);
		System.out.println("==========loginProc End==========");
		
		
		// 2. DB에 값이 있는지 select해서 확인
		// UserDao에서 구현 
		
		
		// 패스워드는 절때 session(JSSID)에 포함시키면 안된다.
		Users user = Users.builder()
				.username(username)
				.password(password)
				.build();
		
		UsersDao usersDao = new UsersDao();
		Users userEntity = usersDao.login(user);
		
		if(userEntity != null) {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", userEntity);
			// 한글처리를 위해 reap객체를 건드린다.
			// MIME 타입을 공부해야한다.
			// Http Header에 Content-Type을 공부하고 이해해야 한다.
			Script.href(resp, "index.jsp", "로그인성공");
			
//			// sendRedirect()은 단순한 페이지 이동이다. 이동할때 메세지를 가지고 가야지
//			// 사용자의 UX가 좋아지고 상호적인 통신이 가능한데, 메시지를
//			// 가지고 이동 하는 것이 아니라 쓰이지 않는다.
//			resp.sendRedirect("index.jsp");
			
		} else {
			Script.back(resp, "로그인실패");
		}
	}
	
	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		// 1. 세션 체크
		HttpSession session = req.getSession();
		
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if(user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			// 값을 담는 코드.
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 1. 세션 체크
		HttpSession session = req.getSession();
		
		Users user = (Users) session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if(user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			// 값을 담는 코드.
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 회원수정(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String password = req.getParameter("password");
		String email = req.getParameter("email");

		Users user = Users.builder()
				.id(id)
				.password(password)
				.email(email)
				.build();

		// 싱글톤 패턴으로 바꿔보자.
		UsersDao usersDao = new UsersDao();
		int result = usersDao.update(user);
	
		if(result == 1) {
			// 수정이 일어나면 무조건 컨트롤러를 타고 이동해야지 값을 들고 간다.
			// 아니면 컨트롤러로 이동해야 한다.
			resp.sendRedirect("index.jsp");
		} else {
			// 수정이 실패한다면 보편적으로 이전 페이지로 이동하게 만든다.
			// 나중에 배우면 구현 해본다.
			resp.sendRedirect("user?gubun=updateOne.jsp");
		}
		
	}
	
	public void 회원삭제(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Users user = Users.builder()
				.id(id)
				.build();

		// 싱글톤 패턴으로 바꿔보자.
		UsersDao usersDao = new UsersDao();
		int result = usersDao.delete(user);
	
		if(result == 1) {
			// 세션을 무효화 시켜서 바로 로그아웃이 되버린다.
			HttpSession session = req.getSession();
			session.invalidate();
			// 삭제가 일어나면 무조건 컨트롤러를 타고 이동해야지 값을 들고 간다.
			// 아니면 컨트롤러로 이동해야 한다.
			resp.sendRedirect("index.jsp");
		} else {
			// 수정이 실패한다면 보편적으로 이전 페이지로 이동하게 만든다.
			// 나중에 배우면 구현 해본다.
			resp.sendRedirect("index.jsp");
		}
		
	}
	
}
