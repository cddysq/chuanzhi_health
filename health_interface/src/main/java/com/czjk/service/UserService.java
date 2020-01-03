package com.czjk.service;

import com.czjk.pojo.User;

/**
 * @Author: Haotian
 * @Date: 2020/1/2 18:52
 * @Description: 用户服务接口
 */
public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息, 用户对应角色与权限
     */
    User findByUsername(String username);
}