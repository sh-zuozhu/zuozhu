package com.xiaya.core.controller.bind.method.annotation;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import com.xiaya.core.annotation.UserToken;
import com.xiaya.core.exception.UserTokenInvalidException;
import com.xiaya.core.httputils.UcenterHttpUtils;
import com.xiaya.core.model.User;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.Constants;
import com.xiaya.core.pojo.StatusCode;

/**
 * 参数解析器
 * @author izene
 *
 */
public class UserTokenMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserTokenMethodArgumentResolver.class);
	
	@Autowired
	private UcenterHttpUtils ucenterHttpUtils;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		final UserToken userToken = getUserTokenAnnotation(parameter);
		return null != userToken;
	}
	
	private UserToken getUserTokenAnnotation(MethodParameter parameter){
		 UserToken parameterAnnotation = parameter.getParameterAnnotation(UserToken.class);
		 return parameterAnnotation;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		final UserToken userToken = getUserTokenAnnotation(parameter);
		if(null != userToken){
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			//获取cookie
			Cookie cookie = WebUtils.getCookie(request, Constants.USER_TOKEN);
			//从cookie中取值
			if(null == cookie){
				throw new UserTokenInvalidException(StatusCode.NO_LOGIN, "please login");
			}
			String token = cookie.getValue();
			emptyTokenException(token);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("coder:token:{}", token);
			}
			if(StringUtils.isNotEmpty(token)){
				token = URLEncoder.encode(token, Constants.ENCODING);
			}
			emptyTokenException(token);
			ActionResult ar = this.ucenterHttpUtils.getUserId(token);
			if(null == ar || !ar.getCode().equals(StatusCode.SUCCESS_CODE)){
				throw new UserTokenInvalidException(StatusCode.NO_LOGIN, "please login");
			}
			User user = new User();
			user.setUserId((String)ar.getData());
			user.setToken(token);
			return user;
		}
		return null;
	}
	
	private void emptyTokenException(String token) throws UserTokenInvalidException{
		if(StringUtils.isEmpty(token))
			throw new UserTokenInvalidException(StatusCode.NO_LOGIN, "please login");
	}
	
}
