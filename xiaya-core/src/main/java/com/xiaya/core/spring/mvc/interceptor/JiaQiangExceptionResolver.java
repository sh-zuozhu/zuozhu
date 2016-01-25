package com.xiaya.core.spring.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xiaya.core.exception.B5mException;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.spring.mvc.support.JsonResult;
public class JiaQiangExceptionResolver implements HandlerExceptionResolver {

	private final static Logger logger = LoggerFactory.getLogger(JiaQiangExceptionResolver.class);
	
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
		}else{
			errorCode =  hasJsonResult && StringUtils.isNotEmpty(jsonResult.errorCode()) ? jsonResult.errorCode() : StatusCode.SERVER_ERROR_CODE;
			errorMsg = hasJsonResult && StringUtils.isNotEmpty(jsonResult.desc()) ? (jsonResult.desc() + "执行失败") : StatusCode.SERVER_ERROR.getMessage();
		}
		return new ActionResult(errorCode, errorMsg, false);
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv =new ModelAndView();
		logger.error("发生异常+++++++++++++++++++++++");
		return mv;
	}
	
	
	
}