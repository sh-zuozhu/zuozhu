package com.xiaya.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.spring.mvc.support.JsonResult;
import com.xiaya.rpc.service.IUserMobileService;

/**
 * 和手机短信相关业务
 * @author izene
 */
@Controller
@RequestMapping("user/mobile")
public class UserMobileController {
	
	@Autowired
	private IUserMobileService userMobileService;

	/**
	 * @param mobile
	 * @param busiCode 不同的busiCode对应不同的短信内容
	 * @return
	 */
	@RequestMapping("/getCode")
	@JsonResult(desc = "获取短信验证码")
	public ActionResult getMobileCode(String mobile, String busiCode){
		if(StringUtils.isEmpty(mobile)) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "手机号不能为空");
		return this.userMobileService.getMobileCode(mobile, busiCode);
	}
}
