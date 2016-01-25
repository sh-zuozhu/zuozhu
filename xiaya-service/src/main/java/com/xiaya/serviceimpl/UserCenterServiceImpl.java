package com.xiaya.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaya.mapper.UserCenterMapper;
import com.xiaya.pojo.UserCenter;
import com.xiaya.rpc.service.IUserCenterService;

@Service
public class UserCenterServiceImpl implements IUserCenterService{

	@Autowired
	private UserCenterMapper userCenterMapper;
	
	@Override
	public UserCenter getUserByMobile(String mobile){
		UserCenter uc = new UserCenter();
		uc.setMobile("15821044389");
		UserCenter user = this.userCenterMapper.selectOne(uc);
		//Example example = new Example(UserCenter.class);
		System.out.println(user);
		return user;
	}
	
}
