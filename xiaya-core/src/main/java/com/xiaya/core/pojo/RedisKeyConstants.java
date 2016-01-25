package com.xiaya.core.pojo;

/**
 * redis key 前缀
 * @author izene
 *
 */
public class RedisKeyConstants {
	public final static String UCENTER_KEY = "b5m:uc:";
	public final static String USER_INFO = UCENTER_KEY + "user:info:";
	public final static String USER_LOGIN_STATUS = UCENTER_KEY + "user:login:status:";
	public final static String REQUEST_IS_COMMITTED = UCENTER_KEY + "commited:";
	public final static String IMGAGE_VALIDATE_CODE_KEY = UCENTER_KEY + "imageValidateCode:";
	public final static String THIRD_BIND_KEY_PREFIX = UCENTER_KEY + "thirdbind:";
	public final static String MESSAGE_SIMPLE_CACHE = UCENTER_KEY + "message:simple:";
	public final static String B5M_ALL_PROVINCE = UCENTER_KEY + "all:province:";
	public final static String UNREAD_MESSAGE_COUNT = UCENTER_KEY + "unread:message:";
    public final static String SEND_VOICE_CODE = UCENTER_KEY + "voiceCode:";
    public final static String WEB_VERSION = UCENTER_KEY + "resource";
    public final static String BZ_RECHARGE_POLICY_CACHE_KEY = UCENTER_KEY + "bz:recharge:policy3:";/** 超级帮钻充值优惠相关 */
}
