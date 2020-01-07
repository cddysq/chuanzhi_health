package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Haotian
 * @Date: 2020/1/7 8:40
 * @Description: 地址管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Serializable {
    private static final long serialVersionUID = -5243767030375722029L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 地址名
     */
    private String name;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
}