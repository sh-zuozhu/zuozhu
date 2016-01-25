package com.xiaya.core.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.exception.B5mException;
import com.xiaya.core.factory.HttpClientFactory;
import com.xiaya.core.http.RequestIdentityHolder;
import com.xiaya.core.pojo.RequestInfo;
import com.xiaya.core.pojo.StatusCode;

public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	/**
	 * 执行http请求
	 * @param method
	 * @param cookies
	 * @return
	 */
	private static String http(HttpMethodBase method, List<Cookie> cookies){
		String response = null;
		try {
			RequestInfo requestInfo = RequestIdentityHolder.get();
			if(null != requestInfo){
				method.addRequestHeader(RequestIdentityHolder.RID, requestInfo.getId());
				method.addRequestHeader(RequestIdentityHolder.RSTEP, requestInfo.getStep() + "");
				method.addRequestHeader(RequestIdentityHolder.RNAME, URLEncoder.encode(StringUtils.trimToEmpty(requestInfo.getName()), "UTF-8"));
				method.addRequestHeader(RequestIdentityHolder.RVERSION, StringUtils.trimToEmpty(requestInfo.getVersion()));
			}
			HttpClient client = HttpClientFactory.getHttpClient();
			if(null != cookies){
				for (Cookie cookie : cookies) {
					if(logger.isDebugEnabled()){
						logger.debug("添加cookie:{}", cookie.toString());
					}
					client.getState().addCookie(cookie);
				}
				cookies.clear();
			}
			client.executeMethod(method);
			Cookie[] c;
			if(null != cookies && (null != (c = client.getState().getCookies()))){
				for (int i = 0; i < c.length; i++) {
					if(logger.isDebugEnabled()){
						logger.debug("收集cookie:{}", c[i].toString());
					}
					cookies.add(c[i]);
				}
			}
			response = method.getResponseBodyAsString();
			if(logger.isDebugEnabled()){
				logger.debug("HTTP返回:{}", response);
			}
		}  catch (Exception e) {
			logger.warn("HTTP请求执行失败", e);
		}
		return response;
	}
	
	/**
	 * 带cookie的post请求
	 * @param url
	 * @param params
	 * @param cookies
	 * @return
	 */
	public static String post(String url, Map<String, String> params, List<Cookie> cookies){
		String response = null;
		if(logger.isDebugEnabled()){
			logger.debug("POST方式请求:{} , 参数:{}", url, params);
		}
		PostMethod method = new PostMethod(url);//放地址
		if (params instanceof PostRawParameter) {
			String text = ((PostRawParameter) params).getText();
			if (StringUtils.isNotEmpty(text)) {
				try {
					RequestEntity requestEntity = new StringRequestEntity(text, "text/plain;charset=utf-8", "UTF-8");
					method.setRequestEntity(requestEntity);
				} catch (UnsupportedEncodingException e) {
					if (logger.isWarnEnabled()) {
						logger.warn("系统不支持UTF-8编码");
					}
					throw new B5mException(StatusCode.SERVER_ERROR_CODE);
				}
			}
		} else {
			method.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			NameValuePair[] pairs = buildNameValuePairs(params);// 组装参数
			if (null != pairs) {
				method.setRequestBody(pairs);
			}
		}
		response = http(method, cookies);
		return response;
		
	}
	
	/**
	 * 组装参数
	 * @param params
	 * @return
	 */
	private static NameValuePair[] buildNameValuePairs(Map<String, String> params){
		List<NameValuePair> pairs = null;
		if(ObjectUtils.isNotEmpty(params)){
			Object[] keys = params.keySet().toArray();
			pairs = new ArrayList<NameValuePair>(keys.length + 1);
			for (int i = 0; i < keys.length; i++) {
				String key = (String) keys[i];
				pairs.add(new NameValuePair(key, params.get(key)));
			}
		}
		return pairs == null ? null : pairs.toArray(new NameValuePair[pairs.size()]);
	}
	
	private static Map<String, String> buildParameter(Object parameter) throws Exception {
		Map<String, String> paramMap = null;
		if(parameter instanceof Map){
			paramMap = new HashMap<String, String>(((Map<?, ?>) parameter).size());
			for (Entry<?, ?> entry : ((Map<?, ?>)parameter).entrySet()){
				if(null != entry.getKey() && null != entry.getValue()){
					paramMap.put(entry.getKey().toString(), entry.getValue().toString());
				}
			}
		}else  if(parameter instanceof CharSequence){
			paramMap = new PostRawParameter(parameter.toString());
		}else if(null != parameter){
			paramMap = new HashMap<String, String>();
			Class<? extends Object> clz = paramMap.getClass();
			Method[] methods = clz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if(methods[i].getName().startsWith("get")){
					Object value = methods[i].invoke(parameter);
					if(null != value){
						String name = methods[i].getName().replace("get", "");
						name = name.substring(0, 1).toLowerCase() + name.substring(1);
						paramMap.put(name, value.toString());
					}
				}
			}
		}
		return paramMap;
	}
	
	/**
	 * 执行http post 请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params){
		return post(url, params, null);
	}
	
	public static String post(String url, Object params) throws Exception{
		return post(url, buildParameter(params), null);
	}
	
	/**
	 * 获取cookie的post请求
	 * @param url
	 * @param params
	 * @param cookies
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Object params, List<Cookie> cookies) throws Exception{
		return post(url, buildParameter(params), cookies);
	}
	
	public static String get(String url, Map<String, String> params, List<Cookie> cookies){
		String response = null;
		if(logger.isDebugEnabled()){
			logger.debug("GET方式请求:{} , 参数:{}", url, params);
		}
		GetMethod method = new GetMethod(url);
		method.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		NameValuePair[] pairs = buildNameValuePairs(params);//组装参数
		if(null != pairs){
			method.setQueryString(pairs);
		}
		response = http(method, cookies);
		return response;
	}
	
	/**
	 * http get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, String> params){
		return get(url, params, null);
	}
	
	public static String get(String url, Object params) throws Exception{
		return get(url, buildParameter(params));
	}
	
	public static String get(String url, Object params, List<Cookie> cookies) throws Exception{
		return get(url, buildParameter(params), cookies);
	}
}

class PostRawParameter implements Map<String, String>{

	private String text;

	public PostRawParameter(String text){
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public String get(Object key) {
		return null;
	}

	@Override
	public String put(String key, String value) {
		return null;
	}

	@Override
	public String remove(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
	}

	@Override
	public void clear() {
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Collection<String> values() {
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return null;
	}

	@Override
	public String toString() {
		return this.text == null ? "null" : text;
	}
	
}
