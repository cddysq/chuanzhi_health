package com.czjk.dao;

import com.czjk.pojo.User;

/**
 * @Author: Haotian
 * @Date: 2020/1/2 19:21
 * @Description: 用户数据接口
 */
public interface UserDao {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}
