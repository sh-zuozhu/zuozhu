package com.xiaya.core.pojo;


import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 可使用exception-*.properties配置文件自动生成异常信息的异常状态码
 * 
 * @date 2015-8-20
 */
public class StatusCode implements Serializable {

	private static final long serialVersionUID = -4122245658000161453L;
	
	public final static String SUCCESS_CODE = "2000";
	public final static StatusCode SUCCESS = new StatusCode(SUCCESS_CODE);
	public final static String SERVER_ERROR_CODE = "50000000000";
	public final static StatusCode SERVER_ERROR = new StatusCode(SERVER_ERROR_CODE);
	public static final String PARAMETER_EEROR_CODE = "40000000000";
	public static final StatusCode PARAMETER_EEROR = new StatusCode(PARAMETER_EEROR_CODE);
	public static final StatusCode DUBBO_SERVICE_EEROR = new StatusCode("50000000001");
	
	public static final String PARAMS_EEROR_CODE = "40010000002";
	public static final String PARAMS_EMPTY_CODE = "40010000003";
	public static final String PARAMS_NOT_VALIDATE_CODE = "40010000004";
	/***********************用户中心相关状态码**********************************/
	public static final String NO_LOGIN = "40010000001";
	/***********************用户中心相关状态码**********************************/
	
	
	private String code;
	private String message;
	
	public StatusCode(String code){
		this.code = StringUtils.isEmpty(code) ? SERVER_ERROR_CODE : code;
	}
	
	public StatusCode(String code, String message){
		this.code = StringUtils.isEmpty(code) ? SERVER_ERROR_CODE : code;
		this.message = StringUtils.trimToEmpty(message);
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public String toString(){
		return code + "(" + message + ")";
	}
	
	
}
