package com.xiaya.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.utils.JsonUtils;

public class RequestParamAndResponseJsonFilter extends AbstractFilter{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RequestParamAndResponseJsonFilter.class);
	


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//获取请求的url,参数
		String url = ((HttpServletRequest) request).getServletPath();
		String parameterMap = JsonUtils.toJson(((HttpServletRequest) request).getParameterMap());
		LOGGER.info("请求访问[{}],请求参数[{}]", url, parameterMap);
		chain.doFilter(request, response);
	}

}
