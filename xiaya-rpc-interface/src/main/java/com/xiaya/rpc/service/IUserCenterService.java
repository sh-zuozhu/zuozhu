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

	/**
	 * 用户登录
	 * @param userCenter
	 * @return
	 */
	ActionResult login(UserCenter userCenter);

	/**
	 * 校验用户名或者email是否注册过
	 * @param userCenter
	 * @return
	 */
	ActionResult isNameOrEmailUse(UserCenter userCenter);

	/**
	 * 验证手机号是否注册过
	 * @param mobile
	 * @return
	 */
	ActionResult isMobileUse(String mobile);

	/**
	 * 验证手机号除了自己之外是否注册过
	 * @param mobile
	 * @param userId
	 * @return
	 */
	ActionResult isMobileUseExceptMine(String mobile, String userId);

}
