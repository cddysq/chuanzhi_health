package com.czjk.dao;

import com.czjk.pojo.Role;

import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2020/1/2 19:23
 * @Description: 角色数据接口
 */
public interface RoleDao {
    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return 用户对应角色集合
     */
    Set<Role> findByUserId(Integer userId);
}
