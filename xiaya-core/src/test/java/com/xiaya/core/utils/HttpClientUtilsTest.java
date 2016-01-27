package com.xiaya.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaya.core.httputils.HttpClientUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class HttpClientUtilsTest {
	
	@Autowired
	private HttpClientUtils httpClientUtils;

	@SuppressWarnings({ "static-access"})
	@Test
	public void testPostStringMapOfStringStringListOfCookie() {
		String url = "http://ucenter.stage.com/user/third/whether/weixinExist.htm";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", "15821044389");
		List<Cookie> cookies = new ArrayList<Cookie>();
		Cookie c = new Cookie();
		c.setName("token");
		cookies.add(c);
		String string = this.httpClientUtils.post(url, params, cookies);
		System.out.println(string);
	}

	@Test
	public void testGetStringMapOfStringStringListOfCookie() {
		
	}

}
