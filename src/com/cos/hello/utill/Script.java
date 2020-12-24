package com.cos.hello.utill;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	
	// 메세지를 띄우면서 이동하게 구현했다.
	public static void href(HttpServletResponse resp, String url, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("location.href='"+url+";'");
		out.println("</script>");
		out.flush();
	}
	
	public static void back(HttpServletResponse resp, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("history.back();");
		out.println("</script>");
		out.flush();
	}
}
