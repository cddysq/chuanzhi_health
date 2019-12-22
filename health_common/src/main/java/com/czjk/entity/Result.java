package com.czjk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 14:19
 * @Description: 封装返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result implements Serializable {
    private static final long serialVersionUID = 5508478016882007638L;

    /**
     * 执行结果，true为执行成功 false为执行失败
     */
    private boolean flag;

    /**
     * 返回结果信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;
}