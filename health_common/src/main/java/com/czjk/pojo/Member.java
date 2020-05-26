package com.czjk.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/26 14:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements Serializable {
    private static final long serialVersionUID = 5904519693998446628L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 档案号
     */
    private String fileNumber;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 备注
     */
    private String remark;
}