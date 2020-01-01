package com.czjk.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.czjk.constant.MessageConstant;
import com.czjk.constant.RedisMessageConstant;
import com.czjk.entity.Result;
import com.czjk.pojo.Member;
import com.czjk.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/1 11:33
 * @Description: 处理会员相关操作
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 手机号快速登录
     *
     * @return 失败回显 or 成功跳转首页
     */
    @PostMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map<String, Object> map) {
        String telephone = Convert.toStr( map.get( "telephone" ) );
        String validateCode = Convert.toStr( map.get( "validateCode" ) );
        //从Redis中获取缓存的登录验证码，key为手机号+RedisConstant.SENDTYPE_LOGIN
        String validateCodeInRedis = jedisPool.getResource().get( telephone + RedisMessageConstant.SENDTYPE_LOGIN );
        //校验手机验证码
        if (validateCodeInRedis == null || !validateCodeInRedis.equals( validateCode )) {
            //验证码错误直接回显
            return Result.builder().flag( false ).message( MessageConstant.VALIDATECODE_ERROR ).build();
        } else {
            //验证码正确判断是否为会员
            Member member = memberService.findByTelephone( telephone );
            if (member == null) {
                //不是会员，自动注册
                member = Member.builder().phoneNumber( telephone ).build();
                memberService.add( member );
            }
            //登录成功，向客户端浏览器写入Cookie，内容为手机号
            Cookie cookie = new Cookie( "login_member_telephone", telephone );
            cookie.setPath( "/" );
            //cookie有效期30天
            cookie.setMaxAge( 60 * 60 * 24 * 30 );
            response.addCookie( cookie );
            //将会员信息保存到Redis,有效期半小时
            String json = JSON.toJSON( member ).toString();
            jedisPool.getResource().setex( telephone, 60 * 30, json );
            //登录成功
            return Result.builder().flag( true ).message( MessageConstant.LOGIN_SUCCESS ).build();
        }
    }
}
