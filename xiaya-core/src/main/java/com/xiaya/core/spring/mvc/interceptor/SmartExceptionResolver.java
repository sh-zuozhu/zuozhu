package com.xiaya.core.spring.mvc.interceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.xiaya.core.exception.B5mException;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.spring.mvc.support.JsonResult;
import com.xiaya.core.spring.mvc.support.JsonResultHandler;
import com.xiaya.core.utils.JsonUtils;

/**
 * 能够自动封装业务执行失败结果的异常处理器抽象基类
 * @author izene
 *
 */
public abstract class SmartExceptionResolver extends SimpleMappingExceptionResolver{
	
	private final Logger logger = LoggerFactory.getLogger(SmartExceptionResolver.class);
	
	@Resource
	private JsonResultHandler jsonResultHandler;
	@Autowired(required = false)
	private Set<String> ingoreExceptions;
	@Autowired(required = false)
	private List<String> ingoreExcetpionCodes;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		ModelAndView mv = null;
		if(handler instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod) handler;
			MethodParameter returnType = method.getReturnType();
			String actionName = returnType.getContainingClass().getSimpleName() + "." + returnType.getMember().getName();
			JsonResult jsonResult = jsonResultHandler.getJsonResult(returnType);//支持通过JsonResult定义错误返回,因此此处尝试获取jsonResult配置
			boolean jsonResultSupport = jsonResult != null;
			if(jsonResultSupport && StringUtils.isNotEmpty(jsonResult.desc())){//如果action配置了JsonResult且指定了desc信息
				actionName += ":" + jsonResult.desc();//生成actionName
			}
			try {
				processException(request, handler, actionName, ex);//处理异常
			} catch (Exception e) {
				if(logger.isWarnEnabled()){
					logger.warn("处理Action接口异常信息出错", e);
					logger.warn("Action接口异常", ex);
				}
			}
			
			try {
				if(jsonResultSupport){
					if(logger.isDebugEnabled()){
						logger.debug("当前Action支持JsonResult返回结果,正调用JsonResultHandlerMethodReturnValueHandler处理器渲染结果");
					}
					jsonResultHandler.writeResponse(getErrorResponse(jsonResult, ex), returnType, this.getDefaultJsonResult(), request, response);
					mv = new ModelAndView();
				}else if(!this.canUserJsonResult(method, returnType)){//询问是否强制使用JsonResult渲染
					if(logger.isDebugEnabled()){
						logger.debug("未在接口{}的Action方法及Controller上找到JsonResult注解配置,将使用默认配置强制渲染数据", actionName);
					}
					jsonResultHandler.writeResponse(getErrorResponse(jsonResult, ex), returnType, this.getDefaultJsonResult(), request, response);
					mv = new ModelAndView();
				}
			} catch (IOException e) {
				if(logger.isWarnEnabled()){
					logger.warn("回传响应数据到客户时出现IO错误", e);
				}
			}
			
		}else{
			logger.warn("未知的处理器类型:" + handler.getClass().getName());
		}
		if(null != mv){
			return mv;
		}
		if(logger.isDebugEnabled()){
			logger.debug("基于配置参数处理当前异常");
		}
		mv = super.doResolveException(request, response, handler, ex);
		return null == mv ? new ModelAndView() : mv;
	}
	
	public void setJsonResultHandler(JsonResultHandler jsonResultHandler) {
		this.jsonResultHandler = jsonResultHandler;
	}

	public void setIngoreExceptions(Set<String> ingoreExceptions) {
		this.ingoreExceptions = ingoreExceptions;
	}
	
	public void setIngoreExcetpionCodes(List<String> ingoreExcetpionCodes) {
		this.ingoreExcetpionCodes = ingoreExcetpionCodes;
	}
	
	/**
	 * 无JsonResult的接口是否强制使用JsonResult渲染结果
	 * @param handler
	 * @param returnType
	 * @return
	 */
	protected boolean canUserJsonResult(HandlerMethod handler, MethodParameter returnType){
		return false;
	}
	
	/**
	 * 处理异常信息---将日常信息打印到日志系统
	 * @param request
	 * @param handler
	 * @param actionName
	 * @param ex
	 */
	protected void processException(HttpServletRequest request, Object handler, String actionName, Exception ex){
		boolean ingoreStack = (ingoreExceptions != null && ingoreExceptions.contains(ex.getClass().getName())) 
				|| (ex instanceof B5mException && ingoreExcetpionCodes != null && ingoreExcetpionCodes.contains(((B5mException)ex).getCode()));
		if(ingoreStack){
			if(logger.isTraceEnabled()){
				logger.trace(String.format("接口{}调用失败,HTTP参数={}", actionName, JsonUtils.toJson(request.getParameterMap())), ex);
			}else if(logger.isWarnEnabled()){
				 logger.warn(String.format("接口{}调用失败,HTTP参数={}", actionName, JsonUtils.toJson(request.getParameterMap())));
			}
		}else if(logger.isWarnEnabled()){
			logger.warn(String.format("接口{}调用失败,HTTP参数={}", actionName, JsonUtils.toJson(request.getParameterMap())), ex);
		}
	}
	
	/**
	 * 包装错误返回信息
	 * @param jsonResult
	 * @param exception
	 * @return
	 */
	protected Object getErrorResponse(JsonResult jsonResult, Exception exception){
		boolean hasJsonResult = jsonResult != null;
		String errorCode;
		String errorMsg;
		if(exception instanceof MissingServletRequestParameterException){
			errorCode = StatusCode.PARAMETER_EEROR_CODE;
			errorMsg = "缺少必须的参数:" + ((MissingServletRequestParameterException)exception).getParameterName();
		}else if(exception instanceof B5mException){
			errorCode = ((B5mException)exception).getCode();
			errorMsg = ((B5mException)exception).getMessage();
		}else{
			errorCode = hasJsonResult && StringUtils.isNotEmpty(jsonResult.errorCode()) ? jsonResult.errorCode() : StatusCode.SERVER_ERROR_CODE;
			errorMsg = hasJsonResult && StringUtils.isNotEmpty(jsonResult.desc()) ? (jsonResult.desc() + "执行失败") : StatusCode.SERVER_ERROR_CODE;
		}
		return new ActionResult(errorCode, errorMsg, false);
	}
	
	protected JsonResult getDefaultJsonResult(){
		return new JsonResult() { //默认JsonResult配置
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return JsonResult.class;
			}
			
			@Override
			public boolean useUnicode() {
				return false;
			}
			
			@Override
			public String errorCode() {
				return "-1";
			}
			
			@Override
			public String desc() {
				return "";
			}
			
			@Override
			public boolean debug() {
				return true;
			}
			
			@Override
			public boolean convertString() {
				return false;
			}
			
			@Override
			public String callback() {
				return "jsonpCallback";
			}
		};
	}
	
}
