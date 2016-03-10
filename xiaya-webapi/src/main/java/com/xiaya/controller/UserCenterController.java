package com.xiaya.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaya.core.annotation.UserToken;
import com.xiaya.core.model.User;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.spring.mvc.support.JsonResult;
import com.xiaya.pojo.UserCenter;
import com.xiaya.rpc.service.IUserCenterService;


/**
 * 用户中心
 * @author izene
 *
 */
@RequestMapping("user")
@Controller
public class UserCenterController {

	@Autowired
	private IUserCenterService userCenterService;
	
	@JsonResult(desc="获取用户信息2")
	@RequestMapping(value="user/info")
	public ModelAndView getUserInfo(String account, Model model){
		ModelAndView mv = new ModelAndView();
		UserCenter user = this.userCenterService.getUserByMobile(account);
		model.addAttribute("user", user);
		return mv;
	}
	
	@JsonResult(desc="获取用户信息")
	@RequestMapping("user/user")
	public ActionResult getUcenter(@UserToken User user,  String account){
		UserCenter uc = this.userCenterService.getUserByMobile(account);
		return new ActionResult(StatusCode.SUCCESS_CODE, "query success", uc);
	}
	
	@JsonResult(desc="获取用户信息")
	@RequestMapping("user")
	public ActionResult getUser(String account){
		UserCenter uc = this.userCenterService.getUserByMobile(account);
		return new ActionResult(StatusCode.SUCCESS_CODE, "query success", uc);
	}
	
	@JsonResult(desc="用户注册")
	@RequestMapping("register")
	public ActionResult register(UserCenter userCenter, String mobileCode){
		return this.userCenterService.register(userCenter, mobileCode);
	}
	
	@JsonResult(desc = "用户登录")
	@RequestMapping("login")
	public ActionResult login(UserCenter userCenter){
		return this.userCenterService.login(userCenter);
	}
	
	@JsonResult(desc = "验证用户或者email是否注册过")
	@RequestMapping("isNameOrEmailUse")
	public ActionResult isNameOrEmailUse(UserCenter userCenter){
		return this.userCenterService.isNameOrEmailUse(userCenter);
	}
	
	@JsonResult(desc = "验证手机号是否被注册过,验证手机号除了自己之外是否被注册")
	@RequestMapping({"isMobileUse","isMobileUseExceptMine"})
	public ActionResult isMobileUse(String mobile, String userId){
		if(StringUtils.isEmpty(mobile)){
			return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "mobile is empty");
		}
		if(StringUtils.isEmpty(userId) && StringUtils.isNotEmpty(mobile)){
			return this.userCenterService.isMobileUse(mobile);
		}else if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(mobile)){
			return this.userCenterService.isMobileUseExceptMine(mobile, userId);
		}
		return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "params isn't validate");
	}
}
