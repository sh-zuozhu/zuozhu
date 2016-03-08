package com.xiaya.rpc.service;

import com.xiaya.core.pojo.ActionResult;
import com.xiaya.pojo.UserCenter;

public interface IUserCenterService {

	/**
	 * 获取用户信息
	 * @param mobile
	 * @return
	 */
	UserCenter getUserByMobile(String mobile);

	/**
	 * 用户注册
	 * @param user
	 * @param mobileCode
	 * @return
	 */
	ActionResult register(UserCenter userCenter, String mobileCode);

}
