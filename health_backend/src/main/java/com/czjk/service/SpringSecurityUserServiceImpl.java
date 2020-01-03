package com.czjk.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.czjk.pojo.Permission;
import com.czjk.pojo.Role;
import com.czjk.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2020/1/2 18:48
 * @Description: 动态为用户授权
 */
@Component
public class SpringSecurityUserServiceImpl implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库获取用户信息
        User user = userService.findByUsername( username );
        if (user == null) {
            //用户名不存在
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            //遍历角色集合，为用户授予角色
            list.add( new SimpleGrantedAuthority( role.getKeyword() ) );
            Set<Permission> permissions = role.getPermissions();

            for (Permission permission : permissions) {
                //遍历权限集合，为用户授予权限
                list.add( new SimpleGrantedAuthority( permission.getKeyword() ) );
            }
        }
        return new org.springframework.security.core.userdetails.User( username, user.getPassword(), list );
    }
}