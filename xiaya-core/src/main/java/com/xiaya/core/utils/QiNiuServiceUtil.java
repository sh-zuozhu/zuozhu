package com.xiaya.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;

public class QiNiuServiceUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QiNiuServiceUtil.class);
	
	private static HttpClient httpClient;
	
	private static String CONTENT_TYPE = "application/json";
	private static String CHARSET = "utf-8";
	
	private static String QINIU_URL;
	private static String UPLOAD_URL = "/imageUpload";
	private static String DELETE_URL = "/removeImage";
	
	private static Properties properties;
	
	private static String userName;
	private static String identityTopic;
	private static String avatarTopic;
	
	private static String imagePrefix;
	
	static{
		properties = (Properties) ContextUtils.getBean("properties");
		QINIU_URL = properties.getProperty("b5m.qiniu.url");
		userName = properties.getProperty("b5m.qiniu.userName");
		identityTopic = properties.getProperty("b5m.qiniu.topic.realidentity");
		avatarTopic = properties.getProperty("b5m.qiniu.topic.avatar");
		imagePrefix = properties.getProperty("b5m.qiniu.image.prefix");
		//多段的连接管理器,而不是默认的简单连接管理器
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		httpClient =  new HttpClient(connectionManager);
		//每个主机最大连接数和总共最大连接数,通过hostConfiguration设置host来区分每个主机
		HttpConnectionManager hcm = httpClient.getHttpConnectionManager();
		hcm.getParams().setDefaultMaxConnectionsPerHost(8);
		hcm.getParams().setMaxTotalConnections(48);
		hcm.getParams().setConnectionTimeout(5000);
		hcm.getParams().setSoTimeout(5000);
		hcm.getParams().setTcpNoDelay(true);
		//失败的情况下会进行3次尝试,成功之后不会再尝试
		hcm.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
	}
	
	public static ActionResult imageUpload(String userName, String topic, byte[] imageDate){
		PostMethod postMethod = new PostMethod(QINIU_URL + UPLOAD_URL);
		try {
			LOGGER.debug("开始调用图片上传服务, url: {}", QINIU_URL + UPLOAD_URL);
			Part[] parts = {
				new StringPart("userName", userName),
				new StringPart("topic", topic),
				new FilePart(String.valueOf(System.currentTimeMillis()), new ByteArrayPartSource("", imageDate))
			};
			
			MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
			postMethod.setRequestEntity(mre);
			httpClient.executeMethod(postMethod);
			
			String body = postMethod.getResponseBodyAsString();
			String code = JsonUtils.getString(body, "code");
			if("200".equalsIgnoreCase(code)){
				 String data = JsonUtils.getString(JsonUtils.getString(body, "data"), "data");
				 return new ActionResult(StatusCode.SUCCESS_CODE, "上传图像成功",imagePrefix + data);
			}else{
				return new ActionResult(StatusCode.HTTP_GET_INFO_CODE, "请求图片上传服务异常", body);
			}
		} catch (Exception e) {
			return new ActionResult(StatusCode.SERVER_INTERNET_CODE, "服务器连接失败!url:" + QINIU_URL + UPLOAD_URL);
		}finally {
			if(null != postMethod){
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
	}
	
	
	public static ActionResult removeImage(String userName, String topic, String imageUrl) {
		PostMethod postMethod = new PostMethod(QINIU_URL + DELETE_URL);
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userName", userName);
			params.put("topic", topic);
			params.put("imageUrl", imageUrl);
			StringRequestEntity sre = new StringRequestEntity(JsonUtils.toJson(params), CONTENT_TYPE, CHARSET);
			postMethod.setRequestEntity(sre);

			LOGGER.info("begin 开始调用图片删除服务，url： {}", QINIU_URL + DELETE_URL);
			httpClient.executeMethod(postMethod);
			String body = postMethod.getResponseBodyAsString();
			if ("200".equalsIgnoreCase(JsonUtils.getString(body, "code"))) {
				String data = JsonUtils.getString(JsonUtils.getString(body, "data"), "data");
				return new ActionResult(StatusCode.SUCCESS_CODE, "删除图像成功", imagePrefix + data);
			} else {
				return new ActionResult(StatusCode.HTTP_GET_INFO_CODE, "删除图片服务异常", body);
			}

		} catch (Exception e) {
			return new ActionResult(StatusCode.SERVER_INTERNET_CODE, "服务器连接失败!url:" + QINIU_URL + DELETE_URL);
		} finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
	}
	
	/**
	 * 上传身份证件
	 * @param imageDate
	 * @return
	 */
	public static ActionResult uploadIdentity(byte[] imageDate){
		return imageUpload(userName, identityTopic, imageDate);
	}
	
	/**
	 * 上传头像
	 * @param imageDate
	 * @return
	 */
	public static ActionResult uploadAvatar(byte[] imageDate){
		return imageUpload(userName, avatarTopic, imageDate);
	}
	
	/**
	 * 删除头像
	 * @param imageUrl
	 * @return
	 */
	public static ActionResult removeAvatar(String imageUrl){
		return removeImage(userName, avatarTopic, imageUrl);
	}
	
	/**
	 * 删除身份证
	 * @param imageUrl
	 * @return
	 */
	public static ActionResult removeIdentity(String imageUrl){
		return removeImage(userName, avatarTopic, imageUrl);
	}
}
