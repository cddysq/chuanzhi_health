package com.czjk.constant;

/**
 * redis缓存验证码常量
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/23 15:38
 **/
public class RedisMessageConstant {

    /**
     * 用于缓存体检预约时发送的验证码
     */
    public static final String SENDTYPE_ORDER = "001";

    /**
     * 用于缓存手机号快速登录时发送的验证码
     */
    public static final String SENDTYPE_LOGIN = "002";

    /**
     * 用于缓存找回密码时发送的验证码
     */
    public static final String SENDTYPE_GETPWD = "003";
}