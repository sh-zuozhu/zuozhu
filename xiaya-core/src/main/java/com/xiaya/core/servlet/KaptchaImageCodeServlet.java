package com.xiaya.core.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.xiaya.core.enums.CacheTimes;
import com.xiaya.core.pojo.RedisKeyConstants;
import com.xiaya.core.utils.CookieUtils;
import com.xiaya.core.utils.RedisUtils;

public class KaptchaImageCodeServlet extends HttpServlet implements Servlet{

	private static final long serialVersionUID = -1475217137705209516L;
	private static final Logger logger = LoggerFactory.getLogger(KaptchaImageCodeServlet.class);
	
	private Properties props = new Properties();
	private Producer kaptchaProducer = null;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		ImageIO.setUseCache(false);
		Enumeration<?> initParams = conf.getInitParameterNames();
		while(initParams.hasMoreElements()){
			String key = (String) initParams.nextElement();
			String value = conf.getInitParameter(key);
			this.props.put(key, value);
		}
		Config config = new Config(props);
		this.kaptchaProducer = config.getProducerImpl();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("pragma", "no-cache");
		response.setContentType("image/jpeg");
		
		ServletOutputStream out = null;
		//创建有文字的图片
		try {
			String sessionId = getSessionIdFromCookie(request, response);
			String capText = this.kaptchaProducer.createText();
			BufferedImage bi = this.kaptchaProducer.createImage(capText);
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			logger.info("KaptchaValidateCodeServlet/sessionId: [{}], capText: [{}]", sessionId, capText);
			//缓存设置10分钟
			RedisUtils.setCache(RedisKeyConstants.IMGAGE_VALIDATE_CODE_KEY + sessionId, capText, CacheTimes.TENMINS);
			String cache = RedisUtils.getCache(RedisKeyConstants.IMGAGE_VALIDATE_CODE_KEY + sessionId, String.class);
			logger.info("验证码:{}", cache);
		} catch (IOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("create validata image failed", e);
			}
		}finally{
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					logger.warn("close outputstream failed", e);
				}
			}
		}
		
	}
	
	/**
	 * 获取cookie name为b5m_session_id对应的value,并且像cookie中设置
	 * @param request
	 * @param response
	 * @return
	 */
	private static String getSessionIdFromCookie(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cks = request.getCookies();
		String sessionId = CookieUtils.getCookieValue("b5m_session_id", cks);
		if(StringUtils.isEmpty(sessionId)){
			sessionId = request.getSession().getId();
		}
		CookieUtils.setNormalCookie("b5m_session_id", sessionId, response);
		return sessionId;
	}
	
	/**
	 * 获取sessionId
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request){
		Cookie[] cks = request.getCookies();
		return CookieUtils.getCookieValue("b5m_session_id", cks);
	}

}
