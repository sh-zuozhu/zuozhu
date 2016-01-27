package com.xiaya.core.model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -2269476422598352922L;
	
	private String token;
	private String userId;
	
	public User(String token, String userId) {
		super();
		this.token = token;
		this.userId = userId;
	}

	public User() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User [token=" + token + ", userId=" + userId + "]";
	}
	
}
