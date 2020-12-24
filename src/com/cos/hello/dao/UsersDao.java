package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {
	
	// Users는 String username, String password, String email를 가지고 있어서 
	// Users 타입을 인자로 받아서 코드의 길이를 줄인다.
	// 재사용하고 함수의 역할을 정확히 하기위해서 insert라는 이름으로 짓는다.
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
	
	public Users login(Users users){
		
		StringBuffer sb = new StringBuffer();
		// append()로 긴 문자열을 나눌땐 append사이에 꼭 띄어쓰기를 넣어줘야 SQL문에서 오류가 생기지 않는다.
		sb.append("SELECT id, username, email FROM users ");
		sb.append("WHERE username = ? AND password = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, users.getUsername());
			pstmt.setString(2, users.getPassword());
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// select가 실패할 경우.
		return null;
	}
	
	// 함수 당 하나의 일만 하도록 해라 if로 분기해서 사용하면 안된다.
	public Users selectById(int id){
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, username, password, email FROM users ");
		sb.append("WHERE id = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.password(rs.getString("password"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//실패할 경우.
		return null;
	}
	
	
	public int update(Users user) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE users SET password = ?, email = ? ");
		sb.append("WHERE id = ?");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			
			// DML쿼리는 전부 Update()로 받는다.
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("INSERT 실패.");
		}

		// INSERT가 실패할 경우
		return -1;
	}
	
	public int delete(Users user) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM users WHERE id = ? ");
		String sql = sb.toString();
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getId());
			
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
