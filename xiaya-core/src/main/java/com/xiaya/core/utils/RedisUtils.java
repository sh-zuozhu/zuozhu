package com.xiaya.core.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaya.core.enums.CacheTimes;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * redis工具类
 * @author izene
 *
 */
public class RedisUtils {
	
	private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	//设置默认缓存有效时间1天
	public final static CacheTimes DEFAULT_CACHE_TIME = CacheTimes.ONEDAY;
	
	private static String redisServer;
	private static JedisCluster jedisCluster;
	private static GenericObjectPoolConfig poolConfig;
	
	static {
		poolConfig = (GenericObjectPoolConfig)ContextUtils.getBean("jedisPoolConfig");
		Properties properties = (Properties)ContextUtils.getBean("properties");
		redisServer = (String) properties.get("redis.servers");
		loadRedisServers();
		
		ConvertUtils.register(new DateConverter(null), Date.class);
		ConvertUtils.register(new DateConverter(null), Timestamp.class);
		ConvertUtils.register(new DateConverter(null),java.util.Date.class);
		
	}
	
	private static void loadRedisServers(){
		Set<HostAndPort> jedisClusterNodes =new HashSet<HostAndPort>();
		String[] redisServers = redisServer.split(",");
		for (String item : redisServers) {
			String[] hostPort = item.split("\\:");
			if(hostPort.length == 2){
				jedisClusterNodes.add(new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1])));
			}
		}
		jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
	}
	
	/**
	 * 默认设置redis缓存
	 * @param key
	 * @param value
	 */
	public static void setCache(String key, Object value){
		setCache(key, value, DEFAULT_CACHE_TIME);
	}
	
	/**
	 * 设置redis,缓存包括有效时间
	 * @param key
	 * @param value
	 * @param cacheTimes
	 */
	public static void setCache(String key, Object value, CacheTimes cacheTimes){
		if(StringUtils.isNotEmpty(key)){
			setCache(key, value, cacheTimes.getTime());
		}
	}
	
	public static void setCache(String key, Object value, int exp){
		try {
			jedisCluster.set(key, JsonUtils.toJson(value));
			if(exp != CacheTimes.FOREVER.getTime()){
				jedisCluster.expire(key, exp);
			}
		} catch (Exception e) {
			logger.error("set cache error:{}", e);
		}
	}
	
	public static String getJsonValue(String key){
		return jedisCluster.get(key);
	}
	
	/**
	 * 获取缓存对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T getCache(String key, Class<T> clazz){
		T result = null;
		try {
			if(StringUtils.isNotEmpty(key)){
				result = JsonUtils.parse2Bean(jedisCluster.get(key), clazz, true);
			}
		} catch (Exception e) {
			logger.error("redis error", e);
			return null;
		}
		return result;
	}
	
	public static <T> List<T> getListCache(String key, Class<T> beanClass) {
		List<T> result = null;
		if (StringUtils.isNotEmpty(key)) {
			try {
				JsonUtils.parse2BeanList(jedisCluster.get(key), beanClass);
			} catch (Exception e) {
				logger.error("get list cache error: {}", e);
				return null;
			}
		}
		return result;
	}
	
	/**
	 * 清空某缓存对象
	 * @param key
	 */
	public static void cleanCache(String key){
		try {
			jedisCluster.del(key);
		} catch (Exception e) {
			logger.error("clean cache error :{}", e);
		}
	}
	
	public static long incr(String key, int by){
		try {
			return jedisCluster.incrBy(key, by);
		} catch (Exception e) {
			logger.error("incr cache error:{}", e);
		}
		return 0;
	}
	
	/**
	 * 计量增加操作
	 * @param key
	 * @param by
	 * @param def
	 * @param expire
	 * @return
	 */
	public static long incr(String key, long by, long def, int expire){
		try {
			Boolean exists = jedisCluster.exists(key);
			if(exists){
				return jedisCluster.incrBy(key, by);
			}else{
				setCache(key, def, expire);
				return def;
			}
		} catch (Exception e) {
			logger.error("incr cache error:{}", e);
		}
		return 0;
	}
	
	public static void decr(String key, int by){
		try {
			jedisCluster.decrBy(key, by);
		} catch (Exception e) {
			logger.error("decr cache error:{}", e);
		}
	}
	
	public static boolean exists(String key){
		try {
			return jedisCluster.exists(key);
		} catch (Exception e) {
			logger.error("invoke cache error:{}", e);
		}
		return false;
	}
	
	public static long ttl(String key){
		try {
			return jedisCluster.ttl(key);
		} catch (Exception e) {
			logger.error("redis error:{}", e);
		}
		return 0;
	}
	
	public static boolean add(String key, Object value, int expire){
		synchronized(key){
			Boolean exists = jedisCluster.exists(key);
			setCache(key, value, expire);
			return !exists;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void hset(String key, Object o, int expire){
		try {
			Map<String, Object> describe = PropertyUtils.describe(o);
			if(null == describe)return;
			hmset(key, describe, expire);
		} catch (Exception e) {
			logger.error("invoke cache hset error:{}", e);
		} 
	}
	
	public static void hset(String key, Object o, CacheTimes cacheTime){
		hset(key, o, cacheTime.getTime());
	}
	
	public static void hset(String key, Object o){
		hset(key, o, DEFAULT_CACHE_TIME.getTime());
	}
	
	public static void hset(String key, String field, Object o, int exp){
		try {
			jedisCluster.hset(key, field, JsonUtils.toJson(o));
			if(CacheTimes.FOREVER.getTime() != exp){
				jedisCluster.expire(key, exp);
			}
		} catch (Exception e) {
			logger.error("invoke cache hset error:{}", e);
		}
	}
	
	public static void hset(String key, String field, Object o){
		hset(key, field, o, DEFAULT_CACHE_TIME.getTime());
	}
	
	public static void hmset(String key, Map<String, Object> map, int exp){
		try {
			jedisCluster.hmset(key, objToJson(map));
			if(CacheTimes.FOREVER.getTime() != exp){
				jedisCluster.expire(key, exp);
			}
		} catch (Exception e) {
			logger.error("invoke cache hmset error:{}", e);
		}
	}
	
	public static void hmset(String key, Map<String, Object> map){
		hmset(key, map, DEFAULT_CACHE_TIME.getTime());
	}
	
	public static <T> T hget(String key, String field, Class<T> clazz){
		try {
			return JsonUtils.parse2Bean(jedisCluster.hget(key, field), clazz, true);
		} catch (Exception e) {
			logger.error("invoke cache hget error:{}", e);
		}
		return null;
	}
	
	public static String hsget(String key){
		try {
			
			return jedisCluster.get(key);
		} catch (Exception e) {
			logger.error("invoke cache hget error:{}", e);
		}
		return null;
	}
	
	public static Object hget(String key, String field){
		return hget(key, field, Object.class);
	}
	
	public static List<Object> hmget(String key, String... field){
		try {
			List<String> list = jedisCluster.hmget(key, field);
			if(null == list) return null;
			List<Object> result = new ArrayList<Object>();
			for (String value : list) {
				result.add(JsonUtils.parse2Bean(value, Object.class, true));
			}
			return result;
		} catch (Exception e) {
			logger.error("invoke cache hmget error:{}", e);
		}
		return null;
	}
	
	public static <T> T hmget(Class<T> clazz, String key, String... field){
		List<Object> values = hmget(key, field);
		if(null != values){
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < field.length; i++) {
				map.put(field[i], values.get(i));
			}
			return populate(clazz, map);
		}
		return null;
	}
	
	public static Map<String, Object> hgetAll(String key){
		try {
			return jsonToObj(jedisCluster.hgetAll(key));
		} catch (Exception e) {
			logger.error("invoke cache hmget error:{}", e);
		}
		return null;
	}
	
	public static <T> T hgetAll(String key, Class<T> clazz){
		return populate(clazz, hgetAll(key));
	}
	
	private static Map<String, Object> jsonToObj(Map<String, String> map){
		if(null == map) return null;
		Map<String, Object> result = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			result.put(key, JsonUtils.parse2Bean(map.get(key), Object.class, true));
		}
		return result;
	}
	
	private static <T> T populate(Class<T> clazz, Map<String, Object> map){
		if(null == map || map.isEmpty()) return null;
		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception e) {
			logger.error("invoke cache hmget error:{}", e);
		}
		return null;
	}
	
	
	private static Map<String, String> objToJson(Map<String, Object> map){
		if(null == map)return null;
		Map<String, String> result = new HashMap<String, String>();
		for (String k : map.keySet()) {
			result.put(k, JsonUtils.toJson(map.get(k)));
		}
		return result;
	}
	
	
}
