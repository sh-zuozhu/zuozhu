package com.xiaya.core.httputils;

import com.xiaya.core.pojo.ActionResult;

import retrofit.http.GET;
import retrofit.http.Query;

public interface UcenterHttpUtils {
	
	/**
	 * 根据ck1获取userId
	 * @param token
	 * @return
	 */
	@GET(value = "/sys/getuserinfo.htm")
	ActionResult getUserId(@Query("tk") final String token);
	
	/**
	 * 根据userId判断是否登录
	 * @param userId
	 * @return
	 */
	@GET(value = "/sys/islogin.htm")
	boolean whetherIsLogin(@Query("userId") final String userId);
}
