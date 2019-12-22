package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 13:37
 * @Description: 用户
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -6169920196479424965L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String station;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 对应角色集合
     */
    private Set<Role> roles = new HashSet<Role>( 0 );
}