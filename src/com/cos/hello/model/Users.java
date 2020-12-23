package com.cos.hello.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
	private int id; //Primary key, Auto_increment
	private String username;
	private String password;
	private String email;
	// 시간은 생략
	}
