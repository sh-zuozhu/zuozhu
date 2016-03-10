package com.xiaya.serviceimpl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.utils.DateUtils;
import com.xiaya.core.utils.PWCode;
import com.xiaya.core.utils.ValidateParamsUtils;
import com.xiaya.mapper.UserCenterMapper;
import com.xiaya.pojo.UserCenter;

/**
 *抽象出service层通用代码以及非重写接口代码 
 * @author izene
 */
@Service
public class BaseService {
	
	@Autowired
	private UserCenterMapper userCenterMapper;

	/**
	 * 初始化用户信息
	 * @param userCenter
	 * @return
	 */
	protected UserCenter initUserInfo(UserCenter userCenter){
		userCenter.setUserId(PWCode.getUUID());
		userCenter.setActiveCode(PWCode.getUUID());
		userCenter.setCreateTime(new Date());
		userCenter.setUpdatedTime(userCenter.getCreateTime());
		userCenter.setPassword(PWCode.getPassWordCode(userCenter.getPassword()));
		return userCenter;
	}
	
	/**
	 * 注册,校验参数
	 * @param userCenter
	 * @param mobileCode
	 * @return
	 */ 
	protected ActionResult validateRegisterParams(UserCenter userCenter, String mobileCode){
				//校验参数
				if(null == userCenter) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "用户信息对象为空", null); 
				if(StringUtils.isEmpty(userCenter.getMobile()) && StringUtils.isEmpty(userCenter.getEmail()) && StringUtils.isEmpty(userCenter.getUserName()))
					return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "参数非法", null);
				if(StringUtils.isEmpty(userCenter.getPassword())) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "密码不能为空", null);
				if(StringUtils.isEmpty(mobileCode)) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "手机短信验证码不能为空", null);
				if(StringUtils.isNotEmpty(userCenter.getMobile()) && !ValidateParamsUtils.regMobile(userCenter.getMobile())){
					return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "手机号格式不正确", null);
				}
				if(StringUtils.isNotEmpty(userCenter.getEmail()) && !ValidateParamsUtils.regEmail(userCenter.getEmail())){
					return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "邮箱格式不正确", null);
				}
				if(StringUtils.isNotEmpty(userCenter.getPassword()) && !ValidateParamsUtils.regPassword(userCenter.getPassword())){
					return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "密码格式不正确", null);
				}
				if(StringUtils.isNotEmpty(userCenter.getMobile())){
					UserCenter user = this.userCenterMapper.selectOne(new UserCenter(userCenter.getMobile()));
					if(null != user){
						return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "您的手机号码在" + DateUtils.Date2String(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "已注册,如忘记密码,请通过手机或者邮箱找回");
					}
				}
				return new ActionResult(StatusCode.SUCCESS_CODE, "success");
	}
	
}
