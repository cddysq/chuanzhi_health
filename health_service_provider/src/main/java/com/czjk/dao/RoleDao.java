package com.czjk.dao;

import com.czjk.pojo.Role;

import java.util.Set;

/**
 * 角色数据接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/2 17:28
 **/
public interface RoleDao {
    /**
     * 根据用户id查询角色
     *
     * @param userId 用户id
     * @return 用户对应角色集合
     */
    Set<Role> findByUserId(Integer userId);
}