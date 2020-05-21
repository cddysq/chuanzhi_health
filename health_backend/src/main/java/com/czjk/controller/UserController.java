package com.czjk.controller;

import com.czjk.constant.MessageConstant;
import com.czjk.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/21 15:02
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前登录用户用户名
     *
     * @return 用户名
     */
    @GetMapping("/getUserName")
    public Result getUserName() {
        //当Spring security完成认证后，会将当前用户信息保存到框架提供的上下文对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return Result.builder().flag( true ).message( MessageConstant.GET_USERNAME_SUCCESS ).data( user.getUsername() ).build();
        }
        return Result.builder().flag( false ).message( MessageConstant.GET_USERNAME_FAIL ).build();
    }
}