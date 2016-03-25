package com.xiaya.core.utils;


public class PWCodeTest {
	
	
	

	 public static void main(String[] args) {
	    	//注册签名
			String register =  PWCode.getPassWordCode("b5m" + "15801802998" + "cd83fd8dd7778a356e4a65add43a51fc");
			//app调用修改用户信息签名
			String updateUser = PWCode.getPassWordCode("b5m"+"75cca137c62945d591edfa4d7bad8cd9");
			System.out.println(updateUser);
			System.out.println(register);
			//登录签名
			String login = PWCode.getMD5String("app_ios_bhp" + "15821044389" + "e10adc3949ba59abbe56e057f20f883e");
			System.out.println(login);
			//登录注册加密后的密码
			String password = PWCode.getMD5String("123456");
			System.out.println(password);
			String pwd = PWCode.getMD5Code_64(PWCode.getMD5String("123456"));
			System.out.println(pwd);
			//修改收获地址 encryCode加密串
			String findAddress = PWCode.getPassWordCode("b5m" + "317d8c149ba4437a9906bc250684bc7e");
			System.out.println(findAddress);
		}
	 
	 

}
