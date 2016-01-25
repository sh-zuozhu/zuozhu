package com.xiaya.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.exception.B5mException;

public class CookieUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

	//设置cookie的有效时间 24个小时
	public static int COOKIE_EXP = 60*60*24;
	
	//自动登录cookie 有效时间29天
	public static int AUTO_LOGIN_COOKIE_EXP = 2505600;
	
	public static String getCookieValue(String name,Cookie[] cks){
		String result = null;
		if(null == cks){
			return result;
		}
		
		for (Cookie item : cks) {
			try {
				if(item.getName().equals(name)){
					result = URLDecoder.decode(item.getValue(), "utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("获取key为{}的cookie失败", item.getName());
				throw new B5mException("根据key获取指定cookie值失败", e);
			}
			break;
		}
		return result;
	}
	
	public static String getCookieValue(String key){
		if(StringUtils.isEmpty(key)){
			return null;
		}
		Cookie[] cks = ContextUtils.getInstance().getRequest().getCookies();
		if(ArrayUtils.isEmpty(cks)){
			return null;
		}else{
			return CookieUtils.getCookieValue(key, cks);
		}
			
	}
	
	public static void setCookie(String cookieName, String value,int max_age, boolean flag, HttpServletRequest request, HttpServletResponse response){
		//设置cookie
		try {
			if(StringUtils.isNotEmpty(value)){
				value = URLEncoder.encode(value, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("encode vaule:{}失败!", value);
			throw new B5mException("encode vaule失败!", e);
		}
		Cookie cookie = new Cookie(cookieName, value);
		
		//是否设置跨二级
		if(flag){
			cookie.setPath("/");
			String domain = GlobalWebCfg.getDomai(request.getServerName());
			if(StringUtils.isNotEmpty(domain)){
				cookie.setDomain(domain);
			}
		}
		
		//设置过期时间
		
		cookie.setMaxAge(max_age);
		response.setHeader("P3P", "CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
		response.addCookie(cookie);
	}
	
	/**
	 * 设置cookie 跨二级域名
	 * 
	 * @param cookieName
	 *            cookie名
	 * @return value 
	 *            cookie值
	 * @throws Exception 
	 */
	public static void setCookie(String cookieName, String value,HttpServletRequest request,HttpServletResponse response)  {		
		setCookie(cookieName,value,COOKIE_EXP,true,request,response);
		
	}
	
	public static void setCookie(String cookieName, String value, int max_age, boolean flag){
		setCookie(cookieName, value, max_age, flag, ContextUtils.getInstance().getRequest(), ContextUtils.getInstance().getResponse());
	}
	
	public static void setCookie(String cookieName, String value, int max_age){
		setCookie(cookieName, value, max_age, true, ContextUtils.getInstance().getRequest(), ContextUtils.getInstance().getResponse());
	}
	
	public static void setCookie(String cookieName, String value){
		setCookie(cookieName, value, COOKIE_EXP);
	}
	
	/**
	 * 设置cookie 不跨二级
	 * 
	 * @param cookieName
	 *            cookie名
	 * @return value 
	 *            cookie值
	 * @throws Exception 
	 */
	public static void setNormalCookie(String cookieName, String value,HttpServletResponse response)  {
		setCookie(cookieName,value,COOKIE_EXP,false, null ,response);
	}
	
	/**
	 * 清除cookie
	 * @param cookieName
	 */
	public static void removeCookie(String cookieName){
		setCookie(cookieName, null, 0);
	}
	
	
	
	
	
}
