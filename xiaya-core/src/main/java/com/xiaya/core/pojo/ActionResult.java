package com.xiaya.core.pojo;

import java.io.Serializable;
/**
 * controller层返回结果
 * @author izene
 *
 */
public class ActionResult implements Serializable{

	private static final long serialVersionUID = -530014113928192398L;

	//返回状态码
	private String code;
	//返回信息描述
	private String message;
	//返回结果
	private Object object;
	
	public ActionResult() {
		super();
	}

	public ActionResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ActionResult(String code, String message, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.object = object;
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ActionResult [code=" + code + ", message=" + message + ", object=" + object + "]";
	}
	
}
