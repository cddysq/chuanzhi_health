package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 13:57
 * @Description: 预约设置
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSetting implements Serializable {
    private static final long serialVersionUID = -7112701753109801249L;

    private Integer id;

    /**
     * 预约设置日期
     */
    private Date orderDate;

    /**
     * 可预约人数
     */
    private int number;

    /**
     * 已预约人数
     */
    private int reservations;
}