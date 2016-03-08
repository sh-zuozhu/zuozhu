package com.xiaya.controller;

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
}
