package com.czjk.dao;

import com.czjk.pojo.Permission;

import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2020/1/2 19:26
 * @Description: 权限数据接口
 */
public interface PermissionDao {
    /**
     * 根据用户角色id查询权限
     *
     * @param roleId 角色id
     * @return 角色对应权限集合
     */
    Set<Permission> findByRoleId(Integer roleId);
}