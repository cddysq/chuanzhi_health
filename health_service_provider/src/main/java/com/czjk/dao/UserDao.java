package com.czjk.dao;

import com.czjk.pojo.User;

/**
 * 用户数据接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/2 17:29
 **/
public interface UserDao {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}