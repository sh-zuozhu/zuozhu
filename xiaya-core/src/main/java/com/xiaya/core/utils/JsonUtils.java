package com.xiaya.core.utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson序列化工具
 * 
 * @author 丹青生
 *
 * @date 2015-9-11
 */
public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	 /**
     * 静态化处理
     */
    private final static ObjectMapper objectMapper = new ObjectMapper();
	
    public static String toJson(List<?> list){
        return toJson(list) ;
    }

    public static Map<?, ?> parse2Map(String jsonStr){
        return parse2Bean(jsonStr,Map.class, false);
    }
    
    public static List<?> parse2List(String jsonStr){
        return parse2Bean(jsonStr,List.class, false);
    }


    public static <T> T parse2Bean(String jsonStr, Class<T> valueType, Boolean isIgnore){
        if (StringUtils.isBlank(jsonStr)){
            return null;
        }
        try{
        	if(isIgnore) {
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        	}
        	objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
            return objectMapper.readValue(jsonStr,valueType);
        }catch (Exception e){
        	logger.error("json string to object error", e);
            return null ;
        }
    }

    public static String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
        	logger.error("object to json string error", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> parse2BeanList(String jsonString, Class<T> beanClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);

        }
        return list;
    }
    
    public static String toUnicode(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "\\" + Integer.toHexString(iValue);
			} else {
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}

    /**
     * 从json串中获取指定key的value
     * @param result
     * @param key
     * @return
     */
    public static String getString(String result, String key){
    	if(StringUtils.isNotEmpty(result) && result.startsWith("[")){
    		result = result.substring(1, result.length()-1);
    	}
    	String str = JSONObject.fromObject(result).getString(key);
    	return convertJson(str);
    }
    
    /**
     * 获取指定json的所有value,以","隔开
     * @param result
     * @return
     */
    public static String getValue(String result){
    	Collection<?> values = JSONObject.fromObject(result).values();
    	String str = values.toString();
    	return convertJson(str);
    }
    
    private static String convertJson(String result){
    	if(StringUtils.isNotEmpty(result)){
    		if(result.startsWith("[")){
    			result = result.substring(1, result.length() - 1);
    		}
    		return result;
    	}
    	return null;
    }
    
    public static void main(String[] args) {
		String result = "[{'code':200,'data':[{'code':200,'data':{'1457602855053':'e85b6cbbf156348fe4e0e0883b85a191'},'msg':'success','ok':true}],'ok':true}]";
		String str = getValue(getString(getString(result, "data"), "data"));
		System.out.println(str);
	}
}
