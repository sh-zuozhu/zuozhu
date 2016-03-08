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
import com.xiaya.rpc.service.IUserCenterService;

@Service
public class UserCenterServiceImpl implements IUserCenterService{

	@Autowired
	private UserCenterMapper userCenterMapper;
	
	/**
	 *获取用户信息
	 */
	@Override
	public UserCenter getUserByMobile(String mobile){
		UserCenter uc = new UserCenter();
		uc.setMobile("15821044389");
		UserCenter user = this.userCenterMapper.selectOne(uc);
		//Example example = new Example(UserCenter.class);
		System.out.println(user);
		return user;
	}

	/**
	 * 注册接口
	 */
	@Override
	public ActionResult register(UserCenter userCenter, String mobileCode) {
		//校验逻辑写在service层,controller层不适合写太多的业务逻辑;
		//我一直是建议controller和service层的返回结果尽量一致,这样就会省去很多代码
		ActionResult ar = validateRegisterParams(userCenter, mobileCode);
		if(ar.getCode() != StatusCode.SUCCESS_CODE){
			return ar;
		}
		userCenter = initUserInfo(userCenter);
		//根据email,手机都可以注册
		int num = this.userCenterMapper.insert(userCenter);
		if(num > 0){
			//保存登录状态,todo,单点登录系统
			//注册成功
			return new ActionResult(StatusCode.SUCCESS_CODE, "register success!", null);
		}
		return new ActionResult(StatusCode.SERVER_ERROR_CODE, "register failed!", null);
	}

	/**
	 * 初始化用户信息
	 * @param userCenter
	 * @return
	 */
	private UserCenter initUserInfo(UserCenter userCenter){
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
	private ActionResult validateRegisterParams(UserCenter userCenter, String mobileCode){
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
