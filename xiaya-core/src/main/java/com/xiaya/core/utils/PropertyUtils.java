package com.xiaya.core.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Property工具类,用于处理Property相关功能,如读写Properties文件等
 * @author izene
 *
 */
public class PropertyUtils {

	/**
	 * 搜索和读取classpath下指定配置文件内容(总是不会返回null)
	 * @param classLoader 类加载器
	 * @param config 配置文件名
	 * @return 读取到的Properties配置信息,如果配置文件不存在则返回空的properties对象
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static Properties load(ClassLoader classLoader, String config) throws IOException{
		Properties properties = new Properties();
		Enumeration<URL> urls = null;
		if(classLoader != null ){
			urls = classLoader.getResources(config);
		}else{
			urls = classLoader.getSystemResources(config);
		}
		if(null != urls){
			if(urls.hasMoreElements()){
				properties.load(urls.nextElement().openStream());
			}
		}
		return properties;
	}
}
