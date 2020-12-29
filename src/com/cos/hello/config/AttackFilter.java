package com.cos.hello.config;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AttackFilter implements Filter {

	
	// 2번째 순서거나 마지막 순서가 되어야 한다.
	// 왜냐하면 AttackFilter엔 파싱을 하기 때문이다.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// JoinProc(회원가입할 때) DB에 넣기 전에 받아서 
		//String username = request.getParameter("username");
		
		
		// 구현하는 원리 : 쿼리스트링으로 각각 필터를 거치게 하는 것이 아니라
		// POST요청에 대해 필터로 걸러주면 된다.	
		// doFilter에서는 ServletRequest를 인자로 request를 사용하기
		// HttpServletRequest로 다운 캐스팅을 해줘야 한다.
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String method = req.getMethod();
		System.out.println("method: "+ method);
		if(method.equals("POST")) {
			// username에 <>(꺽쇠)가 들어오는 것을 방어
			// 만약에 꺽쇠가 들어오면 전부 &lt; &gt;로 치환 후 
			// 다시 필터를 타게 할 것이다.
			// 왜 &lt; &gt;로 치환 하는 것일까? 
			// <>를 대신해 &lt; &gt;로 꺽쇠를 출력해주기 때문이다.
			// 왜 꺽쇠등을 방어하는 것일까?
			// 만약 <script>라는 아이디를 생성하고 로그인을 하면, html코드에서 페이지를 읽을 때
			// 코드를 망가트려서 페이지가 제대로 동작하지 못하게 만든다.
			String username = request.getParameter("username");
			username = username.replaceAll("<", "&lt")
				.replaceAll(">", "&gt");
			System.out.println("username: " + username);
		}
					
		System.out.println("공격 방어 필터 실행");
		chain.doFilter(request, response);
	}

}
