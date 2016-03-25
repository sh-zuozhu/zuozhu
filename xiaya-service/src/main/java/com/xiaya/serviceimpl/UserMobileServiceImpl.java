package com.xiaya.serviceimpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b5m.sms.model.task.Message;
import com.b5m.sms.service.task.SMSService;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.utils.ContextUtils;
import com.xiaya.rpc.service.IUserMobileService;

@Service
public class UserMobileServiceImpl implements IUserMobileService{
	
//	@Resource(name = "taskSmsService")
//	private SMSService smsService;

	/**
	 * 获取短信验证码
	 */
	@Override
	public ActionResult getMobileCode(String mobile, String busiCode) {
		SMSService smsService=(SMSService)ContextUtils.getBean("taskSmsService");
		Message message = new Message();
		message.setContent("测试数据");
		message.setMobile("15821044389");
		message.setTaskCode("1233");
		int num = smsService.send(message);
		if(num > 0){
			return new ActionResult(StatusCode.SUCCESS_CODE, "短信发送成功");
		}
		return null;
	}

}


