package com.xiaya.core.utils;

public class PWCodeTest {
	
	
	

	 public static void main(String[] args) {
	    	//注册签名
			String register =  PWCode.getPassWordCode("b5m" + "13766666666" + "96e79218965eb72c92a549dd5a330112");
			System.out.println(register);
			//登录签名
			String login = PWCode.getMD5String("app_android_mydesk" + "15821044002" + "123456");
			System.out.println(login);
			//登录注册加密后的密码
			String password = PWCode.getMD5String("111111");
			System.out.println(password);
		}

}
