package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cos.hello.dto.LoginDto;
import com.cos.hello.model.Users;

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 다운캐스팅을 해줘야 더 많은 함수를 사용할 수 있다.
		HttpServletRequest req = (HttpServletRequest) request;
		String gubun = req.getParameter("gubun");
		
		// 목적 DB에 insert하는 것.
		if(gubun.equals("loginProc")) {
			// 수단이 너무 길다 vaildation체크를 하는 것.
			// 
			if(req.getParameter("username") == null || 
					req.getParameter("password") == null ||
					req.getParameter("username").equals("")||
					req.getParameter("password").equals("")
					) {
				chain.doFilter(request, response);
				return;
			}
			
			// XSS 공격 막기 추가해줘야 한다.
			
			
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			LoginDto dto = new LoginDto();
			dto.setUsername(username);
			dto.setPassword(password);
			
			request.setAttribute("dto", dto);
		}
		
		chain.doFilter(request, response);
	}

}
