package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {
	
	// Users는 String username, String password, String email를 가지고 있어서 
	// Users 타입을 인자로 받아서 코드의 길이를 줄인다.
	public int insert(Users user) {
		
		// 스트링 컬렉션. 스트링 전용 컬렉션.
		// 동기화 되어었어서 동시 접근을 못한다.
		// 긴 문장을 적을 때 사용하면 된다. 주소값이 바뀌지 않기 때문에 메모리를 적게 먹는다.
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			
			// DML쿼리는 전부 Update()로 받는다.
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("INSERT 실패.");
		}
		
			
		// INSERT가 실패할 경우
		return -1;
	}
}
