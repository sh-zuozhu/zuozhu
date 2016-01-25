package com.xiaya.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class GlobalWebCfg {

	private static String bendi = "localhost";
	private static String stage = "ucenter.stage.com";
	private static String prod = "ucenter.prod.com";
	private static String b5m = "ucenter.b5m.com";
	
	public static String getUrl(String hostName){
		if(isLocal(hostName)){
			return bendi;
		}else if(hostName.indexOf(".stage.com") > -1){
			return stage;
		}else if(hostName.indexOf(".prod.com") > -1){
			return prod;
		}else if(hostName.indexOf(".b5m.com") > -1){
			return b5m;
		}else{
			return hostName;
		}
	}
	
	public static String getDomai(String hostName){
		if(isLocal(hostName)){
			return "";
		}else if(hostName.indexOf(".stage.com") > -1){
			return ".stage.com";
		}else if(hostName.indexOf(".prod.com") > -1){
			return ".prod.com";
		}else if(hostName.indexOf(".b5m.com") > -1){
			return ".b5m.com";
		}else{
			return hostName;
		}
	}
	
	private static boolean isLocal(String hostName){
		if(StringUtils.isEmpty(hostName)){
			return true;
		}
		return hostName.startsWith("127.0.0.1") || hostName.startsWith("localhost");
	}
	
	public static String getHttpUrl(HttpServletRequest request){
		return request.getScheme() + "://" 
				+getUrl(request.getServerName()) 
					+ (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") + "/";
	}
	
	public static String getHttpUrl2(HttpServletRequest request){
		return request.getScheme() + "://" 
				+ getUrl(request.getServerName()) 
					+ (request.getServerPort() != 80 ? ":" +request.getServerPort() : "") + request.getContextPath();
	}
}
