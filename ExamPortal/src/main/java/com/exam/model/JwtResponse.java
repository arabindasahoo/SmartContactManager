package com.exam.model;

public class JwtResponse {

	String Token;

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public JwtResponse() {
		super();
 	}

	public JwtResponse(String token) {
		super();
		Token = token;
	}
	
	
}
