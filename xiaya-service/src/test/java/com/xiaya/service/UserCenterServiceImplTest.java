package com.xiaya.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaya.pojo.UserCenter;
import com.xiaya.serviceimpl.UserCenterServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/applicationContext.xml","classpath:/spring/spring-*.xml"})
public class UserCenterServiceImplTest {

	@Autowired
	private UserCenterServiceImpl userCenterServiceImpl;
	
	@Test
	public void testGetUserByMobile() {
		UserCenter userByMobile = this.userCenterServiceImpl.getUserByMobile("15821044389");
		System.out.println(userByMobile);
	}

}
