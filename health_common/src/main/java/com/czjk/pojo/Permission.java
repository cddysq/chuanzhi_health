package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 13:48
 * @Description: 权限
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission implements Serializable {
    private static final long serialVersionUID = -6438331601723669061L;

    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限关键字，用于权限控制
     */
    private String keyword;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色集合
     */
    private Set<Role> roles = new HashSet<Role>( 0 );
}