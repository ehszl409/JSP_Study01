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
		// URI기법: 파일명을 통해서 찾아가는 것.
		// 라우터
		if (gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		} else if (gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");
		} else if (gubun.equals("selectOne")) { // 유저 정보 보기. (인증이 필요한 이미지)
			// jsp로 결과를 던지기 위한 변수.
			String result;
			HttpSession session = req.getSession();
			
			// return Object를 해준다.
			// 왜? 들어가는 값이 Object여서 return도 Object해줘야 한다.
			// 그래서 다운캐스팅해서 저장해야 한다.
			// 100% 세션값이 존재하는지 확인을 한 번 거치는 것이 안정적이다.
			if(session.getAttribute("sessionUser")!= null) { // null이 아닌것 만으로도 인증 확인이다.
				Users user = (Users)session.getAttribute("sessionUser");
				result = "인증되었습니다.";
				System.out.println("user: "+ user);
			} else {
				result = "인증되지 않았습니다.";			
			}
			
			// req를 유지하는 코드.
			req.setAttribute("result", result);
			RequestDispatcher dis = 
					req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
//			// 응답을 해당 페이지에서 해준다.
//			resp.sendRedirect("user/selectOne.jsp");
			
// 			쿠키값을 가져오고 파싱하는 기본 원리.
//			Cookie[] c = req.getCookies();
//			String sessionKey = c[0].getValue(); //값 확인
//			String sessionKey2 = c[1].getValue(); // 값 확인
//			
//			System.out.println("sessionKey: "+sessionKey);
//			System.out.println("sessionKey2: "+sessionKey2);
//			
			
			
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp");
		} else if (gubun.equals("joinProc")) { // 회원가입을 수행하는 if
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
				resp.sendRedirect("auth/login.jsp");
			} else {
				resp.sendRedirect("auth/join.jsp");
			}
			
			
			
			
			

			

		} else if (gubun.equals("loginProc")) {
			// 아이디와 비밀번호를 서버에서 맞는지 비교해야한다.
			// 1. 갑 전달 받기.
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			System.out.println("==========loginProc Start==========");
			System.out.println(username);
			System.out.println(password);
			System.out.println("==========loginProc End==========");
			// 2. DB에 값이 있는지 select해서 확인
			// 생략
			// 패스워드는 절때 session(JSSID)에 포함시키면 안된다.
			Users user = Users.builder()
					.id(1)
					.username(username)
					.build();
			
			// 3. Stateless한 상태를 Stateful한 상태로 바꿔줘야 한다.
			// 힙 영역에 접근하는 메소드
			// 모든 응답에는 JSSID가 쿠키로 추가된다.
			HttpSession session = req.getSession();
			
			session.setAttribute("sessionUser", user);
			
			// 4. 메인 페이지로 이동.
			resp.sendRedirect("index.jsp");
			
			
			
//			쿠키를 서버에 설정하고 클라이언트한테 넘겨주는 기본원리. 요즘에는 사용하지 않는다.
//			// 클라이언트한테 저장한 쿠키값을 다시 돌려줘야한다.
//			// 이 때 이름을 마음대로 하면안되고 "Set-Cookie"과 "sessionKey=Value"라는 규칙을 지켜야 한다.
//			resp.setHeader("Set-Cookie", "sessionKey=9998");
//			
//			// http가 지원하는 Cookie를 저장하는 함수.
//			Cookie myCookie = new Cookie("sessionKey", "9998");
//			resp.addCookie(myCookie);
			
		}
	}
}
