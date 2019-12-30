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
 * @Author: Haotian
 * @Date: 2019/12/30 19:41
 * @Description: 短信验证码
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 用户在线体检预约发送验证码
     *
     * @param telephone 用户手机号
     * @return 验证码发送是否成功
     */
    @PostMapping("/sendAppointmentOrder/{telephone}")
    public Result sendAppointmentOrder(@PathVariable("telephone") String telephone) {
        //随机生成6位数字验证码
        String authCode = RandomUtil.randomNumbers( 6 );
        //给用户发送验证码
        try {
            SMSUtils.sendShortMessage( SMSUtils.VALIDATE_CODE, telephone, authCode );
        } catch (Exception e) {
            e.printStackTrace();
            //验证码发送失败
            return Result.builder().flag( false ).message( MessageConstant.SEND_VALIDATECODE_FAIL ).build();
        }
        //验证码发送成功,将生成的验证码缓存到redis设置存活时时间(5分钟)
        jedisPool.getResource().setex( telephone + RedisMessageConstant.SENDTYPE_ORDER, 300, authCode );
        return Result.builder().flag( true ).message( MessageConstant.SEND_VALIDATECODE_SUCCESS ).build();
    }

}
