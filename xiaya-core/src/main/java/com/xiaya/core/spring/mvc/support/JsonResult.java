package com.xiaya.core.spring.mvc.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将Action接口返回结果渲染为json/jsonp格式的注解标记
 * 
 * @author 丹青生
 *
 * @date 2015-7-14
 */
@Target({ElementType.TYPE, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface JsonResult {

	boolean useUnicode() default false; // 是否使用unicode编码,默认不使用
	String callback() default "jsonpCallback"; // jsonp回调函数名,默认jsonpCallback
	String errorCode() default "-1"; // 接口抛出异常时,按返回消息中的错误码
	String desc() default ""; // 接口说明
	boolean convertString() default false; // 是否强制转换String类型返回值
	boolean debug() default true; // 是否打印返回结果调试信息
	
}

