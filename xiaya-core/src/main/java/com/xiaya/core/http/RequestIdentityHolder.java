package com.xiaya.core.http;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.MDC;
import org.apache.log4j.helpers.ThreadLocalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.pojo.RequestInfo;

/**
 * 请求信息持有者
 * @author izene
 *
 */
@SuppressWarnings("unchecked")
public class RequestIdentityHolder {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RequestIdentityHolder.class);
	private final static ThreadLocalMap HOLDER = new ThreadLocalMap();
	protected static String NAME = 	"";
	protected static String VERSION = "";
	public final static String RID = "rid";
	public final static String RSTEP = "rstep";
	public final static String RIP = "rip";
	public final static String RNAME = "rname";
	public final static String RVERSION = "rversion";
	public final static String CONFIG = "requestinfo.properties";
	
	static{
		ClassLoader classLoader = RequestIdentityHolder.class.getClassLoader();
		Enumeration<URL> urls = null;
		try {
			if(null != classLoader){
				urls = classLoader.getResources(CONFIG);
			}else{
				urls = ClassLoader.getSystemResources(CONFIG);
			}
			if(urls != null){
				
				Properties properties = new Properties();
				if(urls.hasMoreElements()){
					properties.load(urls.nextElement().openStream());
				}
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("classpath:requestinfo.properties配置文件内容:{}", properties.toString());
				}
				NAME = (String)properties.get("name");
				VERSION = (String)properties.get("VERSION");
			}
		} catch (IOException e) {
			if(LOGGER.isWarnEnabled()){
				LOGGER.warn("加载classpath:requestinfo.properties文件失败", e);
			}
		}
	}
	
	/**
	 * 私有构造方法,不允许外部构造
	 */
	private RequestIdentityHolder(){
	}
	
	/**
	 * 设置当前请求信息
	 * @param requestInfo
	 */
	public static void set(RequestInfo requestInfo){
		if(requestInfo != null){
			MDC.put(RID, requestInfo.getId());
			MDC.put(RSTEP, requestInfo.getStep());
			MDC.put(RIP, StringUtils.trimToEmpty(requestInfo.getIp()));
			MDC.put(RNAME, StringUtils.trimToEmpty(requestInfo.getName()));
			MDC.put(RVERSION, StringUtils.trimToEmpty(requestInfo.getVersion()));
			HOLDER.set(requestInfo);
		}else{
			clear();
		}
	}
	
	public static void clear(){
		HOLDER.set(null);
		MDC.remove(RID);
		MDC.remove(RIP);
		MDC.remove(RSTEP);
		MDC.remove(RNAME);
		MDC.remove(RVERSION);
	}
	
	/**
	 * 获取当前请求信息
	 */
	public static RequestInfo get(){
		return (RequestInfo) HOLDER.get();
	}
	
	/**
	 * 获取当前信息
	 * @param autoCreate 如果为null,是否自动创建并自动与当前线程绑定
	 * @return
	 */
	public static RequestInfo get(boolean autoCreate){
		RequestInfo ri = get();
		if(null == ri && autoCreate){
			ri = generateNew();
			set(ri);
		}
		return ri;
	}

	/**
	 * 生成一个新的RequestInfo,但不自动与当前线程绑定
	 * @return
	 */
	public static RequestInfo generateNew(){
		RequestInfo requestInfo = new RequestInfo(generateRid());
		requestInfo.setName(NAME);
		requestInfo.setVersion(VERSION);
		return requestInfo;
	}
	
	public static String generateRid(){
		return RandomStringUtils.randomNumeric(10);
	}
	
	/**
	 * 加入一个已经存在请求调用序列
	 * @param requestInfo 已存在请求调用序列的请求信息
	 * @return 当前线程私有的请求信息,不同于参数中指定的requestInfo
	 */
	public static RequestInfo join(RequestInfo requestInfo){
		if(null != requestInfo){
			requestInfo = requestInfo.clone();
			set(requestInfo);
		}
		return requestInfo;
	}
	
	public static String getName(){
		return NAME;
	}
	
	public static String getVersion(){
		return VERSION;
	}
}
