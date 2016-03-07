package com.xiaya.core.spring.mvc.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 将带有JsonResult注解的action接口的返回结果渲染为json/jsonp格式的返回结果处理器
 * 
 * @author 丹青生
 *
 * @date 2015-7-14
 */
public class JsonResultHandler2 implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		System.out.println("进入参数解析器");
		return false;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		
	}

	
}

