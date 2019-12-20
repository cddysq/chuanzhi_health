package com.czjk.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.service.UserService;

/**
 * @Author: Haotian
 * @Date: 2019/12/20 19:27
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String msg() {
        return "Success";
    }
}
