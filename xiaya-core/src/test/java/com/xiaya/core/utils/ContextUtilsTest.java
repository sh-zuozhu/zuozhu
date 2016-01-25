package com.xiaya.core.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("static-access")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/spring-*.xml"})
public class ContextUtilsTest {
	
	@Autowired
	private ContextUtils contextUtils;

	@Test
	public void testContextUtils() {
	}

	@Test
	public void testGetInstance() {
		ContextUtils instance = this.contextUtils.getInstance();
		System.out.println("+++++++++++++" + instance);
	}

	@Test
	public void test_setApplicationContext() {
	}

	@Test
	public void testGetApplicationContext() {
		ApplicationContext applicationContext = this.contextUtils.getApplicationContext();
		System.out.println(applicationContext);
	}

	@Test
	public void testGetWebApplicationContext() {
	}

	@Test
	public void testGetBeanString() {
	}

	@Test
	public void testGetBeanClassOfT() {
	}

	@Test
	public void testGetBeanStringClassOfT() {
	}

	@Test
	public void test_setRootPath() {
	}

	@Test
	public void testGetRootPath() {
	}

	@Test
	public void testGetRealPath() {
	}

	@Test
	public void test_setContextPath() {
	}

	@Test
	public void testGetContextPath() {
	}

	@Test
	public void test_setIntercepter() {
	}

	@Test
	public void testGetJspIntercepter() {
	}

	@Test
	public void testGetIpAddr() {
	}

	@Test
	public void test_setServlet() {
	}

	@Test
	public void testGetRequest() {
	}

	@Test
	public void testGetResponse() {
	}

	@Test
	public void testDestroy() {
	}

	@Test
	public void testSetApplicationContext() {
	}

}
