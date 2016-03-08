package com.xiaya.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 校验参数工具类
 * @author izene
 *
 */
public class ValidateParamsUtils {
	
	private static String MOBILE_REG = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[57])|(17[0678]))\\d{8}$";
	private static String EMAIL_REG = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	private static String PASSWORD_REG = "^[a-zA-Z_0-9]{6,15}$";
	private static String USER_NAME_REG = "[\u4e00-\u9fa5\\w]+";

	private static String USER_NICKNAME_REG = "^[a-zA-Z_0-9\u4e00-\u9fa5]{2,15}$";
	private static String URL_REG = "^\\b(((http|https?|ftp)://)+(.+))$*";
	private static final int EMAIL_LENGTH = 15;

	 public static boolean regMobile(String mobile){
		 if(StringUtils.isEmpty(mobile)) return false;
		 return regStr(MOBILE_REG, mobile);
	 }
	 
	 public static boolean regEmail(String email){
		 if(StringUtils.isEmpty(email)) return false;
		 boolean flag = email.length() > EMAIL_LENGTH ? false : true;
		 if(flag) flag = regStr(EMAIL_REG, email);
		 return flag;
	 }
	 
	 public static boolean regUrl(String url){
		 if(StringUtils.isEmpty(url)) return false;
		 return regStr(URL_REG, url);
	 }
	 
	 public static boolean regPassword(String password){
		 if(StringUtils.isEmpty(password)) return false;
		 return regStr(PASSWORD_REG, password);
	 }
	 
	 public static boolean regUserName(String userName){
		 if(StringUtils.isEmpty(userName)) return false;
		 boolean flag = regStr(USER_NAME_REG, userName);
		 if(flag){
			 int userNameLength = count(userName);
			 if(userNameLength <4 || userNameLength > 15){
				 flag = false;
			 }
		 }
		 return flag;
	 }
	 
	 public static boolean regNickName(String nickName){
		 if(StringUtils.isEmpty(nickName)) return false;
		 return regStr(USER_NICKNAME_REG, nickName);
	 }
	 
	 /**
	  * 计算字符串长度,汉字算两个字符
	  * @param str
	  * @return
	  */
	 private static int count(String str){
		 if(null == str || str.length() == 0) return 0;
		 int count = 0;
		 char[] chs = str.toCharArray();
		 for (int i = 0; i < chs.length; i++) {
			count += (chs[0] > 0xff) ? 2 : 1;
		}
		 return count;
	 }
	 
	 public static boolean regStr(String regex, String str){
		 if(StringUtils.isEmpty(str)) return false;
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(str);
		 return m.matches();
	 }
		
	 public static void main(String[] args) {
		System.out.println(regMobile("15210442238"));
	}
}
