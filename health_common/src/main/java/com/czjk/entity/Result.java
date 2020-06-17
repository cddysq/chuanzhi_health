package com.czjk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/17 9:39
 **/
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