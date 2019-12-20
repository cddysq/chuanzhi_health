package com.czjk.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Haotian
 * @Date: 2019/12/20 19:29
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/hello")
    public String hello() {
        return userService.msg();
    }

}
