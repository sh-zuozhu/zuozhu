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
	private Object data;
	//ok兼容用户中心字段
	private boolean ok;
	
	public ActionResult() {
		super();
	}

	public ActionResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ActionResult(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ActionResult(String code, String message, Object data, boolean ok) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.ok = ok;
	}

	public boolean getOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ActionResult [code=" + code + ", message=" + message + ", data=" + data + ", ok=" + ok + "]";
	}
	
}
