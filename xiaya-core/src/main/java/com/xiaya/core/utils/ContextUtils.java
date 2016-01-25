package com.xiaya.core.utils;

import java.io.File;
import java.net.URI;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 和spring容器相关的工具类
 * @author izene
 *
 */
public class ContextUtils implements ApplicationContextAware, DisposableBean, ServletContextAware{
	
	private static ContextUtils _this = null;
	private static ApplicationContext _applicationContext = null;
	private static ServletContext _servletContext = null;
	private String rootPath = null;
	private String contextPath = null;
	private HandlerInterceptorAdapter _interceptor = null;
	
	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	
	public ContextUtils() {
	}
	
	public static synchronized ContextUtils getInstance(){
		if(null == _this) _this = new ContextUtils();
		return _this;
	}

	public void _setApplicationContext(ApplicationContext context){
		_applicationContext = context;
	}
	
	/**
	 * 兼容非WebApplication情况下的spring单元测试用
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return _applicationContext;
	}
	
	public static WebApplicationContext getWebApplicationContext(){
		return  (_applicationContext instanceof WebApplicationContext) ? (WebApplicationContext)_applicationContext : null;
	}
	
	/**
	 * 获取spring的bean对象
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		Object obj = null;
		if(_applicationContext.containsBean(beanName)){
			obj = _applicationContext.getBean(beanName);
		}
		return obj;
	}
	
	public static <T> T getBean(Class<T> clazz){
		return _applicationContext.getBean(clazz);
	}
	
	public static <T> T getBean(String beanName, Class<T> clazz){
		T t = null;
		if(_applicationContext.containsBean(beanName)){
			t = (T)_applicationContext.getBean(beanName, clazz);
		}
		return t;
	}
	
	public final void _setRootPath(String path){
		_this.rootPath = path;
	}
	
	public final String getRootPath(){
		return _this.rootPath;
	}
	
	public final String getRealPath(String path){
		File webRoot = new File(_this.rootPath);
		URI webRootUri = webRoot.toURI();
		URI uri = webRootUri.resolve(path);
		return uri.getPath();
	}
	
	public final void _setContextPath(String path){
		_this.contextPath = path;
	}
	
	public final String getContextPath(){
		return _this.contextPath;
	}
	
	/**
	 * 设置并初始化Jsp页面拦截器
	 * @param inc
	 */
	public final void _setIntercepter(HandlerInterceptorAdapter inc){
		_interceptor = inc;
	}
	
	/**
	 * 获取jsp页面拦截器
	 * @return
	 */
	public final HandlerInterceptorAdapter getJspIntercepter(){
		return _this._interceptor;
	}
	
	public final String getIpAddr(){
		String ip = _this.request.get().getHeader("x-forwarded-for");
		if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = _this.request.get().getHeader("Proxy-Client-IP");
		}
		if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = _this.request.get().getHeader("WL_Proxy-Client-IP");
		}
		if(null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = _this.request.get().getRemoteAddr();
		}
		
		if(StringUtils.isNotBlank(ip)){
			int index = ip.indexOf(",");
			if(index > 0){
				ip = ip.substring(0, index);
			}
		}
		
		return ip;
	}
	

	public final void _setServlet(HttpServletRequest request, HttpServletResponse response){
		_this.request.set(request);
		_this.response.set(response);
	}
	
	public final HttpServletRequest getRequest(){
		return _this.request.get();
	}
	
	public final HttpServletResponse getResponse(){
		return _this.response.get();
	}
	
	@Override
	public void destroy() throws Exception {
		_setApplicationContext(null);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		_setApplicationContext(applicationContext);
	}


	@Override
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}
	
	public ServletContext getServletContext(){
		return _servletContext;
	}

	@Override
	public String toString() {
		return "ContextUtils [rootPath=" + rootPath + ", contextPath=" + contextPath + ", _interceptor=" + _interceptor
				+ ", request=" + request + ", response=" + response + "]";
	}
	
}
