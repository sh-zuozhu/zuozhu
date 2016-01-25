package com.xiaya.core.exception;

import org.apache.commons.lang3.StringUtils;

import com.xiaya.core.pojo.StatusCode;

/**
 * 异常对象
 * 
 *
 */
public class B5mException extends RuntimeException {

	private static final long serialVersionUID = -1328224619794339672L;
	
	private String code;
	
	public B5mException(StatusCode code){
		super(code.getMessage());
		this.code = code.getCode();
	}
	
	public B5mException(StatusCode code, Throwable cause){
		super(code.getMessage(), cause);
		this.code = code.getCode();
	}
	
	public B5mException(String message){
		super(StringUtils.trimToEmpty(message));
		this.code = StatusCode.SERVER_ERROR_CODE;
	}
	
	public B5mException(String message, Throwable cause){
		super(StringUtils.trimToEmpty(message), cause);
		this.code = StatusCode.SERVER_ERROR_CODE;
	}
	
	public B5mException(Throwable cause){
		super(cause);
	}
	
	public String getCode() {
		return code;
	}
	
	public String toString() {
        String s = getClass().getName();
        String message =this.code + ":" + getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

}
