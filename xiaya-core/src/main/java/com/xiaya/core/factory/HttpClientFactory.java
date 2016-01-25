package com.xiaya.core.factory;

import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.utils.PropertyUtils;

public class HttpClientFactory {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientFactory.class);
	private final static ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
	private final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	private final static String CONFIG = "httpconfig.properties";
	private static boolean NEED_INIT = true;//是否需要初始化
	private static ReentrantLock LOCK = new ReentrantLock();
	
	/**
	 * 使用默认的配置文件(classpath:httpconfig.properties)初始化连接管理器
	 */
	public static void init(){
		LOCK.lock();
		if(NEED_INIT){
			LOGGER.debug("使用默认配置自动初始化连接管理器");
			init(CONFIG);
		}
		LOCK.unlock();
	}
	
	/**
	 * 使用指定的配置文件初始化连接管理器
	 * @param config
	 */
	public static void init(String config){
		LOCK.lock();
		if(NEED_INIT){
			int maxHostConnections = 8;
			int maxTotalConnections = 48;
			int connectionTimeout = 5000;
			int soTimeout = 5000;
			boolean tcpNoDelay = true;
			boolean retry = true;
			config = StringUtils.isEmpty(config) ? CONFIG : config;
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("尝试读取配置文件[classpath:{}]信息以初始化连接管理器", config);
			}
			
			try {
				ClassLoader classLoader = HttpClientFactory.class.getClassLoader();
				Properties properties = PropertyUtils.load(classLoader, config);
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("classpath:{}配置文件内容:{}", config, properties);
				}
				maxHostConnections = Integer.parseInt(properties.getProperty("maxHostConnections", "8"));
				maxTotalConnections = Integer.parseInt(properties.getProperty("maxTotalConnections", "48"));
				connectionTimeout = Integer.parseInt(properties.getProperty("connectionTimeout", "5000"));
				soTimeout = Integer.parseInt(properties.getProperty("soTimeout", "5000"));
				tcpNoDelay = Boolean.parseBoolean(properties.getProperty("tcpNoDelay", "true"));
				retry = Boolean.parseBoolean(properties.getProperty("retry", "true"));
			} catch (Exception e) {
				if(LOGGER.isWarnEnabled()){
					LOGGER.warn("加载classpath:" + config + "文件失败", e);
				}
			}
			init(maxHostConnections, maxTotalConnections, connectionTimeout, soTimeout, tcpNoDelay, retry);
		}
		LOCK.unlock();
	}
	
	/**
	 * 使用详细参数初始化连接管理器
	 * @param maxHostConnections
	 * @param maxTotalConnections
	 * @param connectionTimeout
	 * @param soTimeout
	 * @param tcpNoDelay
	 * @param retry
	 */
	public static void init(int maxHostConnections, int maxTotalConnections, int connectionTimeout, int soTimeout, boolean tcpNoDelay, boolean retry){
		LOCK.lock();
		if(NEED_INIT){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("使用配置[maxHostConnections={}, maxTotalConnections={}, connectionTimeout={}, soTimeout={}, tcpNoDelay={}, retry={}]初始化连接管理器",
						new Object[]{maxHostConnections, maxTotalConnections, connectionTimeout, soTimeout, tcpNoDelay, retry});
			}
			connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxHostConnections);
			connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
			connectionManager.getParams().setConnectionTimeout(connectionTimeout);
			connectionManager.getParams().setSoTimeout(soTimeout);
			connectionManager.getParams().setTcpNoDelay(tcpNoDelay);
			if(retry){
				connectionManager.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); // 失败的情况下会进行3次尝试,成功之后不会再尝试
			}
		}
		NEED_INIT = false;
		LOCK.unlock();
	}
	
	/**
	 * 获得httpClient每个请求(request)都会取得同样的httpClient,不同请求会获得不同的httpClient
	 * @return
	 */
	public static HttpClient getHttpClient(){
		if(NEED_INIT){
			init();
		}
		HttpClient httpClient = threadLocal.get();
		if(null == httpClient){
			httpClient = new HttpClient(connectionManager);
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			threadLocal.set(httpClient);
		}
		return httpClient;
	}

	
}
