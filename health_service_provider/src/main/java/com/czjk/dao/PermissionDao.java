package com.czjk.dao;

import com.czjk.pojo.Permission;

import java.util.Set;

/**
 * 权限数据接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/2 17:28
 **/
public interface PermissionDao {
    /**
     * 根据用户角色id查询权限
     *
     * @param roleId 角色id
     * @return 角色对应权限集合
     */
    Set<Permission> findByRoleId(Integer roleId);
}