package com.czjk.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.PermissionDao;
import com.czjk.dao.RoleDao;
import com.czjk.dao.UserDao;
import com.czjk.pojo.Permission;
import com.czjk.pojo.Role;
import com.czjk.pojo.User;
import com.czjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 用户服务
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/3 15:13
 **/
@Service(interfaceClass = UserService.class)
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        //查询用户基本信息，不包含用户的角色
        User user = userDao.findByUsername( username );
        if (user == null) {
            return null;
        }
        //根据用户ID查询对应的角色
        Set<Role> roles = roleDao.findByUserId( user.getId() );
        if (CollUtil.isNotEmpty( roles )) {
            for (Role role : roles) {
                //根据角色ID查询关联的权限
                Set<Permission> permissions = permissionDao.findByRoleId( role.getId() );
                if (CollUtil.isNotEmpty( permissions )) {
                    //让角色关联权限
                    role.setPermissions( permissions );
                }
            }
        }
        //让用户关联角色
        user.setRoles( roles );
        return user;
    }
}