package com.xiaya.rpc.service;

import com.xiaya.core.pojo.ActionResult;

public interface IUserMobileService {

	/**
	 * 获取短信验证码
	 * @param mobile
	 * @param busiCode
	 * @return
	 */
	ActionResult getMobileCode(String mobile, String busiCode);

}
