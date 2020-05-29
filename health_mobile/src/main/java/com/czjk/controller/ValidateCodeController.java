package com.czjk.controller;

import cn.hutool.core.util.RandomUtil;
import com.czjk.constant.MessageConstant;
import com.czjk.constant.RedisMessageConstant;
import com.czjk.entity.Result;
import com.czjk.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 短信验证码
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/29 17:15
 **/
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 随机生成6位数字验证码
     */
    private static final String AUTH_CODE = RandomUtil.randomNumbers( 6 );

    /**
     * 用户在线体检预约发送验证码
     *
     * @param telephone 用户手机号
     * @return 验证码发送是否成功
     */
    @PostMapping("/sendAppointmentOrder/{telephone}")
    public Result sendAppointmentOrder(@PathVariable("telephone") String telephone) {
        try {
            //给用户发送验证码
            SMSUtils.sendShortMessage( SMSUtils.VALIDATE_CODE, telephone, AUTH_CODE );
        } catch (Exception e) {
            e.printStackTrace();
            //验证码发送失败
            return Result.builder().flag( false ).message( MessageConstant.SEND_VALIDATECODE_FAIL ).build();
        }
        //验证码发送成功,将生成的验证码缓存到redis设置存活时时间(5分钟)
        jedisPool.getResource().setex( telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, AUTH_CODE );
        return Result.builder().flag( true ).message( MessageConstant.SEND_VALIDATECODE_SUCCESS ).build();
    }

    /**
     * 用户快速登录验证码
     *
     * @param telephone 用户手机号
     * @return 验证码是否发送成功
     */
    @PostMapping("/send4Login/{telephone}")
    public Result send4Login(@PathVariable("telephone") String telephone) {
        try {
            //给用户发送验证码
            SMSUtils.sendShortMessage( SMSUtils.VALIDATE_CODE, telephone, AUTH_CODE );
        } catch (Exception e) {
            e.printStackTrace();
            //验证码发送失败
            return Result.builder().flag( false ).message( MessageConstant.SEND_VALIDATECODE_FAIL ).build();
        }
        //验证码发送成功,将登录验证码缓存到redis设置存活时时间(5分钟)
        jedisPool.getResource().setex( telephone + RedisMessageConstant.SENDTYPE_LOGIN, 300, AUTH_CODE );
        return Result.builder().flag( true ).message( MessageConstant.SEND_VALIDATECODE_SUCCESS ).build();
    }
}