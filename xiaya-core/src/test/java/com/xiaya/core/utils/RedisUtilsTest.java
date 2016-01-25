package com.xiaya.core.utils;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml","classpath:spring/spring-*.xml"})
public class RedisUtilsTest {
	
	@Autowired
	private RedisUtils redisUtils;

	@Test
	public void testSetCacheStringObject() {
		this.redisUtils.setCache("jiaqiang-1", "贾蔷1111343");
	}

	@SuppressWarnings("static-access")
	@Test
	public void testSetCacheStringObjectCacheTimes() {
		//Map<String, Object> hgetAll = this.redisUtils.hgetAll("jiaqiang");
		//String str = this.redisUtils.hsget("贾蔷");
		String str = this.redisUtils.getCache("jiaqiang-1", String.class);
		System.out.println(str);
	}

	@Test
	public void testSetCacheStringObjectInt() {
	}

	@Test
	public void testGetJsonValue() {
	}

	@Test
	public void testGetCache() {
	}

	@Test
	public void testGetListCache() {
	}

	@Test
	public void testCleanCache() {
	}

	@Test
	public void testIncrStringInt() {
	}

	@Test
	public void testIncrStringLongLongInt() {
	}

	@Test
	public void testDecr() {
	}

	@Test
	public void testExists() {
	}

	@Test
	public void testTtl() {
	}

	@Test
	public void testAdd() {
	}

	@Test
	public void testHsetStringObjectInt() {
	}

	@Test
	public void testHsetStringObjectCacheTimes() {
	}

	@Test
	public void testHsetStringObject() {
	}

	@Test
	public void testHsetStringStringObjectInt() {
	}

	@Test
	public void testHsetStringStringObject() {
	}

	@Test
	public void testHmsetStringMapOfStringObjectInt() {
	}

	@Test
	public void testHmsetStringMapOfStringObject() {
	}

	@Test
	public void testHgetStringStringClassOfT() {
	}

	@Test
	public void testHgetStringString() {
	}

	@Test
	public void testHmgetStringStringArray() {
	}

	@Test
	public void testHmgetClassOfTStringStringArray() {
	}

	@Test
	public void testHgetAllString() {
	}

	@Test
	public void testHgetAllStringClassOfT() {
	}

}
