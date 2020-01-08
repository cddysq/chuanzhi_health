package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 13:50
 * @Description: 体检预约信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {
    private static final long serialVersionUID = 8282521965837379502L;

    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";

    /**
     * 主键
     */
    private Integer id;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 预约日期
     */
    private Date orderDate;

    /**
     * 预约类型 电话预约/微信预约
     */
    private String orderType;

    /**
     * 预约状态（是否到诊）
     */
    private String orderStatus;

    /**
     * 预约体检地址
     */
    private String address;

    /**
     * 体检套餐id
     */
    private Integer setmealId;
}