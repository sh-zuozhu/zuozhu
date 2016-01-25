package com.xiaya.core.spring.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaya.core.utils.JsonUtils;

/**
 * HTTP请求记录拦截器
 * 
 * @author 丹青生
 *
 * @date 2015-7-31
 */
public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(RequestLoggerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(LOGGER.isDebugEnabled()){
			String url = request.getRequestURI();
			String paramJson = JsonUtils.toJson(request.getParameterMap());
			LOGGER.debug("请求访问:[{}], 参数:{}", url, paramJson);
		}
		return super.preHandle(request, response, handler);
	}
	
}
