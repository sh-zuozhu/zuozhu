package com.xiaya.core.spring.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.HandlerMethod;

import com.xiaya.core.exception.B5mException;
import com.xiaya.core.exception.UserTokenInvalidException;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.spring.mvc.support.JsonResult;
public class ActionExceptionResolver extends SmartExceptionResolver {

	private final static Logger logger = LoggerFactory.getLogger(ActionExceptionResolver.class);
	
	protected ActionResult getErrorResponse(JsonResult jsonResult, Exception exception){
		boolean hasJsonResult = jsonResult != null;
		String errorCode;
		String errorMsg;
		if(exception instanceof MissingServletRequestParameterException){
			errorCode = StatusCode.PARAMETER_EEROR_CODE;
			errorMsg = "缺少必须的参数:" + ((MissingServletRequestParameterException)exception).getParameterName();
		}else if(exception instanceof B5mException){
			errorCode = ((B5mException)exception).getCode();
			errorMsg =  ((B5mException)exception).getMessage();
		}else if(exception instanceof UserTokenInvalidException){
			errorCode = ((UserTokenInvalidException)exception).getCode();
			errorMsg = ((UserTokenInvalidException)exception).getMessage();
		}else{
			errorCode =  hasJsonResult && StringUtils.isNotEmpty(jsonResult.errorCode()) ? jsonResult.errorCode() : StatusCode.SERVER_ERROR_CODE;
			errorMsg = hasJsonResult && StringUtils.isNotEmpty(jsonResult.desc()) ? (jsonResult.desc() + "执行失败") : StatusCode.SERVER_ERROR.getMessage();
		}
		return new ActionResult(errorCode, errorMsg, false);
	}
	
	@Override
	protected boolean canUserJsonResult(HandlerMethod handler, MethodParameter returntType) {
		return null != returntType && returntType.getContainingClass().getName().indexOf("com.b5m.module") != -1;
	}
	
	@Override
	protected void processException(HttpServletRequest request, Object handler, String actionName, Exception ex) {
		if(ex instanceof MissingServletRequestParameterException){
    		if(logger.isWarnEnabled()){
    			logger.warn("缺少必须的参数:" + ((MissingServletRequestParameterException) ex).getParameterName());
    		}
    	}else {
    		super.processException(request, handler, actionName, ex);
		}
	}
	
	
}