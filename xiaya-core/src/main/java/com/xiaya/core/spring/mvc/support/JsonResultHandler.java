package com.xiaya.core.spring.mvc.support;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiaya.core.exception.B5mException;
import com.xiaya.core.utils.JsonUtils;

/**
 * 将带有JsonResult注解的action接口的返回结果渲染为json/jsonp格式的返回结果处理器
 * 
 * @author 丹青生
 *
 * @date 2015-7-14
 */
public class JsonResultHandler implements HandlerMethodReturnValueHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(JsonResultHandler.class);
	
	@Autowired(required = false)
	private StringHttpMessageConverter stringHttpMessageConverter;
	protected Charset encoding;
	
	public JsonResultHandler(){
		encoding = Charset.forName("UTF-8");
		stringHttpMessageConverter = new StringHttpMessageConverter(encoding);
		stringHttpMessageConverter.setWriteAcceptCharset(false);
	}
	
	public JsonResult getJsonResult(MethodParameter returnType) {
		JsonResult jsonResult = null;
		jsonResult = returnType.getMethodAnnotation(JsonResult.class); // 优先使用action方法上的注解
		if(jsonResult == null){
			jsonResult = AnnotationUtils.findAnnotation(returnType.getContainingClass(), JsonResult.class); // 搜索Controller级别注解
		}
		return jsonResult;
	}
	
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return getJsonResult(returnType) != null;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		mavContainer.setRequestHandled(true);
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		HttpServletResponse response = ((ServletWebRequest) webRequest).getResponse();
		writeResponse(returnValue, returnType, request, response);
	}

	public void writeResponse(Object returnValue, MethodParameter returnType, JsonResult jsonResult, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String controllerName = returnType.getContainingClass().getSimpleName();
		String actionName = returnType.getMethod().getName();
		if(jsonResult == null){
			if(LOGGER.isWarnEnabled()){
				LOGGER.warn("未在接口[{}.{}]的Action方法及Controller上找到JsonResult注解配置", controllerName, actionName);
			}
			throw new B5mException("请指定JsonResult序列化配置");
		}
		String callback = jsonResult.callback();
		String jsonValue = this.convertReturnValue(jsonResult, returnValue);
		MediaType mediaType;
		if(StringUtils.isNotEmpty(callback) && StringUtils.isNotEmpty(callback = request.getParameter(callback))){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("调用方指定了jsonp回调函数名[{}],将返回jsonp格式数据", callback);
			}
			jsonValue = new StringBuilder(callback).append("(").append(jsonValue).append(")").toString();
			mediaType = new MediaType("application", "javascript", encoding);
		}else {
			mediaType = new MediaType("application", "json", encoding);
		}
		if(jsonResult.useUnicode()){
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("使用unicode编码转换返回数据");
			}
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(jsonValue);
			StringBuffer res = new StringBuffer();
			while (m.find()) {
				m.appendReplacement(res, "\\" + JsonUtils.toUnicode(m.group()));
			}
			jsonValue = m.appendTail(res).toString();
		}
		if(jsonResult.debug() && LOGGER.isDebugEnabled()){
			LOGGER.debug("接口[{}.{}:{}]返回结果:{}", new Object[]{controllerName, actionName, jsonResult.desc(), jsonValue});
		}
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
		stringHttpMessageConverter.write(jsonValue, mediaType, outputMessage);
	}
	
	protected String convertReturnValue(JsonResult jsonResult, Object returnValue) {
		String value = null;
		if (returnValue instanceof JsonResultWrapper) { // 包装器类型返回值
			value = JsonUtils.toJson(((JsonResultWrapper)returnValue).getData());
		}else { // 其他未知类型均默认渲染到JSON
			value = JsonUtils.toJson(returnValue);
		}
		return value;
	}
	
	/**
	 * 将响应数据写回客户端
	 * 
	 * @param returnValue action接口返回结果
	 * @param returnType action接口返回值类型
	 * @throws IOException
	 */
	protected void writeResponse(Object returnValue, MethodParameter returnType, HttpServletRequest request, HttpServletResponse response) throws IOException {
		writeResponse(returnValue, returnType, this.getJsonResult(returnType), request, response);
	}

	public void setStringHttpMessageConverter(StringHttpMessageConverter stringHttpMessageConverter) {
		this.stringHttpMessageConverter = stringHttpMessageConverter;
	}
	
}

