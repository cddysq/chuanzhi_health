package com.czjk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/18 14:23
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult implements Serializable {
    private static final long serialVersionUID = 1104113460789764801L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页结果
     */
    private List rows;
}