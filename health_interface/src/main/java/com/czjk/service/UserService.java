package com.czjk.service;

import com.czjk.pojo.User;

/**
 * 用户服务接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/28 16:31
 **/
public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息, 用户对应角色与权限
     */
    User findByUsername(String username);
}