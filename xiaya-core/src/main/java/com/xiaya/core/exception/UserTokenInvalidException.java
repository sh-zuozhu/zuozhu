package com.xiaya.core.exception;

/**
 * 处理token无效,或者未登录
 * @author izene
 *
 */
public class UserTokenInvalidException extends Exception{

	private static final long serialVersionUID = 278130536022082834L;
	
	private String code;
	private String message;
	private String data;
	
	
	public UserTokenInvalidException() {
		super();
	}
	
	public UserTokenInvalidException(String code) {
		super();
		this.code = code;
	}
	
	public UserTokenInvalidException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public UserTokenInvalidException(String code, String message, String data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "UserTokenInvalidException [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	
}
