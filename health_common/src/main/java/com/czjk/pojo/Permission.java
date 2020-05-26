package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/26 14:18
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