package com.xiaya.serviceimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.utils.PWCode;
import com.xiaya.core.utils.ValidateParamsUtils;
import com.xiaya.mapper.UserCenterMapper;
import com.xiaya.pojo.UserCenter;
import com.xiaya.rpc.service.IUserCenterService;

@Service
public class UserCenterServiceImpl extends BaseService implements IUserCenterService {

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
	 * 用户登录
	 */
	@Override
	public ActionResult login(UserCenter userCenter) {
		//登录校验参数
		if(null == userCenter) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "未传入用户信息");
		if(StringUtils.isEmpty(userCenter.getAccount())) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "请输入用户名");
		if(StringUtils.isEmpty(userCenter.getPassword())) return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "请输入密码");
		String account = userCenter.getAccount();
		UserCenter uc = null;
		if(ValidateParamsUtils.regMobile(account)){
			uc = this.userCenterMapper.selectOne(new UserCenter(account));
		}else if(ValidateParamsUtils.regEmail(account)){
			UserCenter record = new UserCenter();
			record.setEmail(account);
			uc = this.userCenterMapper.selectOne(record);
		}
		if(null == uc){
			return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "您输入的账户不存在");
		}
		if(!uc.getPassword().equals(PWCode.getPassWordCode(userCenter.getPassword()))){
			return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "您输入的密码不正确");
		}
		uc.setLastLoginTime(new Date());
		this.userCenterMapper.updateByParamsSelective(uc);
		return new ActionResult(StatusCode.SUCCESS_CODE, "login success");
	}

	/**
	 * 校验用户名或者email是否注册过
	 */
	@Override
	public ActionResult isNameOrEmailUse(UserCenter userCenter) {
		if(null != userCenter && StringUtils.isEmpty(userCenter.getUserName()) && StringUtils.isEmpty(userCenter.getEmail())){
			return new ActionResult(StatusCode.PARAMS_EMPTY_CODE, "请输入用户名或者email!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		List<UserCenter> result;
		if(StringUtils.isNotEmpty(userCenter.getUserName())){
			params.put("userName", userCenter.getUserName());
			result = this.userCenterMapper.selectByMap(params);
			if(result.size() > 0){
				return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "您输入的用户已经注册过了");
			}
			return new ActionResult(StatusCode.SUCCESS_CODE, "您输入的用户可以注册");
		}
		if(StringUtils.isNotEmpty(userCenter.getEmail())){
			params.put("email", userCenter.getEmail());
			result = this.userCenterMapper.selectByMap(params);
			if(result.size() > 0){
				return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "您输入的email已经注册过了");
			}
			return new ActionResult(StatusCode.SUCCESS_CODE, "您输入的email可以注册");
		}
		return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "您输入的参数有误!");
	}

	/**
	 * 验证手机号是否注册过
	 */
	@Override
	public ActionResult isMobileUse(String mobile) {
		if(!ValidateParamsUtils.regMobile(mobile)){
			return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "您输入的手机格式不正确");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		List<UserCenter> result = this.userCenterMapper.selectByMap(params);
		if(result.size() > 0){
			return new ActionResult(StatusCode.MOBILE_REGISTER_CODE, "您的手机号码已经注册过!");
		}
		return new ActionResult(StatusCode.SUCCESS_CODE, "您的手机号可以注册!");
	}

	/**
	 * 验证手机号除了自己之外是否注册过
	 */
	@Override
	public ActionResult isMobileUseExceptMine(String mobile, String userId) {
		if(!ValidateParamsUtils.regMobile(mobile)){
			return new ActionResult(StatusCode.PARAMS_NOT_VALIDATE_CODE, "您输入的手机格式不正确");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("userId", userId);
		List<UserCenter> result = this.userCenterMapper.selectByMap(params);
		if(result.size() > 0){
			return new ActionResult(StatusCode.MOBILE_REGISTER_CODE, "您的手机号码已经注册过!");
		}
		return new ActionResult(StatusCode.SUCCESS_CODE, "您的手机号可以注册!");
	}
	
	
	
}
