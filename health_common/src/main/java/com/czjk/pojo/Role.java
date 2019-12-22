package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 13:40
 * @Description: 角色
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements Serializable {
    private static final long serialVersionUID = 2752042516175552627L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色关键字，用于权限控制
     */
    private String keyword;

    /**
     * 描述
     */
    private String description;

    /**
     * 对应多个用户
     */
    private Set<User> users = new HashSet<User>( 0 );

    /**
     * 对应多个权限
     */
    private Set<Permission> permissions = new HashSet<Permission>( 0 );

    /**
     * 对应多个菜单
     */
    private LinkedHashSet<Menu> menus = new LinkedHashSet<Menu>( 0 );
}