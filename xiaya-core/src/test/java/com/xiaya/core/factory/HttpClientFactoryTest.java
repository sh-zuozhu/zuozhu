package com.xiaya.core.factory;

import org.apache.commons.httpclient.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class HttpClientFactoryTest {
	/**
	 * 添加注解
	 */
	@Autowired
	private HttpClientFactory httpClientFactory;

	@Test
	public void testGetHttpClient() {
		@SuppressWarnings("static-access")
		HttpClient httpClient = this.httpClientFactory.getHttpClient();
		System.out.println(httpClient);
	}

}
