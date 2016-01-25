package com.xiaya.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFilter implements Filter{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractFilter.class);


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("AbstractFilter启动!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	}

	@Override
	public void destroy() {
		LOGGER.info("AbstractFilter摧毁!");
	}
}
