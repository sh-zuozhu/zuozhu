package com.xiaya.core.httputils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaya.core.pojo.ActionResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/spring-*.xml"})
public class UcenterHttpUtilsTest {
	
	@Autowired
	private UcenterHttpUtils ucenterHttpUtils;

	@Test
	public void testGetUserId() {
		ActionResult ar = this.ucenterHttpUtils.getUserId("ac462HHskVdP1u8tMzFg%2Bj%2B2RBw1M2I5ZjTCPgvpD3zjT6AEXLNf0wFasTnZ%2FM78NEt6SH1h9Nn38YzhWfDXzw%3D%3D");
		System.out.println(ar);
	}

	@Test
	public void testWhetherIsLogin() {
		boolean ar = this.ucenterHttpUtils.whetherIsLogin("75cca137c62945d591edfa4d7bad8cd9");
		System.out.println(ar);
	}

}
